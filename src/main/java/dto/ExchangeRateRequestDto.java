package dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
public class ExchangeRateRequestDto {
    private final String baseCurrencyCode;
    private final String targetCurrencyCode;
    private final BigDecimal rate;

    public ExchangeRateRequestDto(String baseCurrencyCode, String targetCurrencyCode, BigDecimal rate) {
        this.baseCurrencyCode = baseCurrencyCode;
        this.targetCurrencyCode = targetCurrencyCode;
        this.rate = rate;
    }

    public ExchangeRateRequestDto(String baseCurrencyCode, String targetCurrencyCode) {
        this.baseCurrencyCode = baseCurrencyCode;
        this.targetCurrencyCode = targetCurrencyCode;
        this.rate = null;
    }


}
