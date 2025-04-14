package rate;

import currency.CurrencyService;
import currency.dto.CurrencyRequestDto;
import currency.dto.CurrencyResponseDto;
import exception.ExchangeRateNotFoundException;
import rate.dto.RateRequestDto;
import rate.dto.RateResponseDto;

public class RateService {
    private static final RateService INSTANCE = new RateService();
    private final RateDao rateDao = RateDao.getInstance();
    private final CurrencyService currencyService = CurrencyService.getInstance();
    public static RateService getInstance() {
        return INSTANCE;
    }

    private RateService() {
    }

    public RateResponseDto findOneExchangeRate(RateRequestDto rateRequestDto) throws ExchangeRateNotFoundException {
        Rate rate = buildPreRate(rateRequestDto);
        rate = rateDao.findOneRate(rate);
        buildFinalRate(rate);
        RateResponseDto rateResponseDto = buildResponseDto(rate);
        if (rateResponseDto.getBaseCurrency() == null || rateResponseDto.getTargetCurrency() == null) {
            throw new ExchangeRateNotFoundException();
        }
        return rateResponseDto;
    }

    public RateResponseDto findAllExchangeRate(RateRequestDto rateRequestDto) {

        return null;
    }

    private Rate buildPreRate(RateRequestDto rateRequestDto) {
        return new Rate(
                null,
                rateRequestDto.getBaseCurrencyCode(),
                null,
                rateRequestDto.getTargetCurrencyCode(),
                null,
                null
        );
    }

    private void buildFinalRate (Rate rate) {
        CurrencyRequestDto baseCurrencyDto = new CurrencyRequestDto(rate.getBaseCurrencyCode());
        CurrencyRequestDto targetCurrencyDto = new CurrencyRequestDto(rate.getTargetCurrencyCode());
        rate.setBaseCurrency(currencyService.findOneCurrency(baseCurrencyDto));
        rate.setTargetCurrency(currencyService.findOneCurrency(targetCurrencyDto));
    }

    private RateResponseDto buildResponseDto (Rate rate) {
        return new RateResponseDto(
                rate.getId(),
                rate.getBaseCurrency(),
                rate.getTargetCurrency(),
                rate.getRate()
        );
    }

}
