package service;

import dto.RateRequestDto;
import dto.RateResponseDto;
import entity.ExchangeRate;

public class RateService {
    public static final RateService INSTANCE = new RateService();

    public static RateService getInstance() {
        return INSTANCE;
    }

    private RateService() {
    }

    public RateResponseDto findOneExchangeRate(RateRequestDto rateRequestDto) {
        ExchangeRate exchangeRate = createExchangeRate(rateRequestDto);


        return null;
    }





    private ExchangeRate createExchangeRate(RateRequestDto rateRequestDto) {
        return new ExchangeRate(
                null,
                rateRequestDto.getBaseCurrencyCode(),
                null,
                rateRequestDto.getTargetCurrencyCode(),
                null,
                null
        );
    }


}
