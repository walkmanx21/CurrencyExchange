package rate.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
@RequiredArgsConstructor
public class RateRequestDto {
    private final String baseCurrencyCode;
    private final String targetCurrencyCode;
    private final BigDecimal rate;

    public RateRequestDto(String baseCurrencyCode, String targetCurrencyCode) {
        this.baseCurrencyCode = baseCurrencyCode;
        this.targetCurrencyCode = targetCurrencyCode;
        this.rate = null;
    }
}


