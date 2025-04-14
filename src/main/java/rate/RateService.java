package rate;

import rate.dto.RateRequestDto;
import rate.dto.RateResponseDto;

public class RateService {
    public static final RateService INSTANCE = new RateService();

    public static RateService getInstance() {
        return INSTANCE;
    }

    private RateService() {
    }

    public RateResponseDto findOneExchangeRate(RateRequestDto rateRequestDto) {
        Rate rate = createExchangeRate(rateRequestDto);


        return null;
    }





    private Rate createExchangeRate(RateRequestDto rateRequestDto) {
        return new Rate(
                null,
                rateRequestDto.getBaseCurrencyCode(),
                null,
                rateRequestDto.getTargetCurrencyCode(),
                null,
                null
        );
    }


}
