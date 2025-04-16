package currencyExchange;

import currency.CurrencyService;
import currency.dto.CurrencyRequestDto;
import currencyExchange.dto.ExchangeRequestDto;
import currencyExchange.dto.ExchangeResponseDto;
import rate.Rate;
import rate.RateDao;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CurrencyExchangeService {
    private static final CurrencyExchangeService INSTANCE = new CurrencyExchangeService();
    private final RateDao rateDao = RateDao.getInstance();
    private final CurrencyService currencyService = CurrencyService.getInstance();

    private CurrencyExchangeService(){
    }

    public static CurrencyExchangeService getInstance() {
        return INSTANCE;
    }

    public ExchangeResponseDto makeCurrencyExchange (ExchangeRequestDto exchangeRequestDto) {
                String currencyCode1 = exchangeRequestDto.getBaseCurrencyCode();
        String currencyCode2 = exchangeRequestDto.getTargetCurrencyCode();
        if (checkExRateInDatabase(currencyCode1, currencyCode2)) {
            Exchange exchange = buildPreExchange(exchangeRequestDto);
            buildPreFinalExchange(exchange,currencyCode1, currencyCode2);
            BigDecimal convertedAmount = exchange.getRate().multiply(exchange.getAmount());
            exchange.setConvertedAmount(convertedAmount);
            return buildExchangeResponseDto(exchange);
        }
        if (checkExRateInDatabase(currencyCode2, currencyCode1)) {
            Exchange exchange = buildPreExchange(exchangeRequestDto);
            buildPreFinalExchange(exchange,currencyCode2, currencyCode1);
            BigDecimal convertedAmount = (new BigDecimal("1.000000")).
                    divide(exchange.getRate(), RoundingMode.FLOOR).
                    multiply(exchange.getAmount());
            convertedAmount = convertedAmount.setScale(2, RoundingMode.FLOOR);
            exchange.setConvertedAmount(finalConvertedAmount);
            return buildExchangeResponseDto(exchange);
        }




        return null;
    }




    private Exchange buildPreExchange (ExchangeRequestDto exchangeRequestDto) {
        return new Exchange(
                exchangeRequestDto.getBaseCurrencyCode(),
                exchangeRequestDto.getTargetCurrencyCode(),
                exchangeRequestDto.getAmount()
        );
    }

    private boolean checkExRateInDatabase (String baseCurrencyCode, String targetCurrencyCode) {
        Rate rate = rateDao.findOneRate(new Rate(baseCurrencyCode, targetCurrencyCode));
        if (rate.getId() != null) {
            return true;
        } else {
            return false;
        }
    }

    private Exchange buildPreFinalExchange(Exchange exchange, String currencyCode1, String currencyCode2) {
        Rate rate = rateDao.findOneRate(new Rate(currencyCode1, currencyCode2));
        exchange.setRate(rate.getRate());
        CurrencyRequestDto currencyRequestDto1 = new CurrencyRequestDto(currencyCode1);
        CurrencyRequestDto currencyRequestDto2 = new CurrencyRequestDto(currencyCode2);
        exchange.setBaseCurrency(currencyService.findOneCurrency(currencyRequestDto1));
        exchange.setTargetCurrency(currencyService.findOneCurrency(currencyRequestDto2));
        return exchange;
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
