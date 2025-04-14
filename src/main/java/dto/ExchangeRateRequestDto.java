package dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@RequiredArgsConstructor
public class ExchangeRateRequestDto {
    private final String baseCurrencyCode;
    private final String targetCurrencyCode;
}
