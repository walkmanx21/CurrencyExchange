package currencyExchange;

import currencyExchange.dto.ExchangeRequestDto;
import currencyExchange.dto.ExchangeResponseDto;
import exception.AnyErrorException;
import rate.RateDao;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CurrencyExchangeService {
    private static final CurrencyExchangeService INSTANCE = new CurrencyExchangeService();
    private final RateDao rateDao = RateDao.getInstance();
    private final ExchangeDao exchangeDao = ExchangeDao.getInstance();

    private CurrencyExchangeService(){
    }

    public static CurrencyExchangeService getInstance() {
        return INSTANCE;
    }

    public ExchangeResponseDto makeCurrencyExchange (ExchangeRequestDto exchangeRequestDto) throws AnyErrorException {
        Exchange exchange = buildPreExchange(exchangeRequestDto);
        String firstCurrencyCode = exchange.getBaseCurrencyCode();
        String secondCurrencyCode = exchange.getTargetCurrencyCode();
        String usd = "USD";
        BigDecimal convertedAmount;

        if (checkExRateInDatabase(firstCurrencyCode, secondCurrencyCode)) {
            exchange.setRate(exchangeDao.getOnlyRateField(firstCurrencyCode, secondCurrencyCode));
            convertedAmount = exchange.getRate().multiply(exchange.getAmount());
            exchange.setConvertedAmount(convertedAmount);
        }

        if (checkExRateInDatabase(secondCurrencyCode, firstCurrencyCode)) {
            BigDecimal reverseRate = exchangeDao.getOnlyRateField(secondCurrencyCode, firstCurrencyCode);
            BigDecimal rate = new BigDecimal("1.000000").divide(reverseRate, RoundingMode.FLOOR);
            exchange.setRate(rate);
            convertedAmount = rate.multiply(exchange.getAmount());
            exchange.setConvertedAmount(convertedAmount);
        }

        if (checkExRateInDatabase(usd, firstCurrencyCode) && checkExRateInDatabase(usd, secondCurrencyCode)) {
            BigDecimal firstCurrencyRate = exchangeDao.getOnlyRateField(usd, firstCurrencyCode);
            BigDecimal secondCurrencyRate = exchangeDao.getOnlyRateField(usd, secondCurrencyCode);
            BigDecimal rate = secondCurrencyRate.divide(firstCurrencyRate, RoundingMode.FLOOR);
            convertedAmount = rate.multiply(exchange.getAmount());
            exchange.setRate(rate);
            exchange.setConvertedAmount(convertedAmount);
        }
        buildFinalExchange(exchange);
        return buildExchangeResponseDto(exchange);
    }


    private Exchange buildPreExchange (ExchangeRequestDto exchangeRequestDto) {
        return new Exchange(
                exchangeRequestDto.getBaseCurrencyCode(),
                exchangeRequestDto.getTargetCurrencyCode(),
                exchangeRequestDto.getAmount()
        );
    }

    private boolean checkExRateInDatabase (String baseCurrencyCode, String targetCurrencyCode) throws AnyErrorException {
        int rateId = exchangeDao.findOneRate(baseCurrencyCode, targetCurrencyCode);
        return rateId != 0;
    }

    private void buildFinalExchange(Exchange exchange) throws AnyErrorException {
        exchange.setBaseCurrency(exchangeDao.findCurrency(exchange.getBaseCurrencyCode()));
        exchange.setTargetCurrency(exchangeDao.findCurrency(exchange.getTargetCurrencyCode()));
    }

    private ExchangeResponseDto buildExchangeResponseDto (Exchange exchange) {
        return new ExchangeResponseDto(
                exchange.getBaseCurrency(),
                exchange.getTargetCurrency(),
                exchange.getRate(),
                exchange.getAmount(),
                exchange.getConvertedAmount()
        );
    }
}
