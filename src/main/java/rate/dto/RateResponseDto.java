package rate.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@RequiredArgsConstructor
public class RateResponseDto {
    private final Integer id;
    private final currency.Currency baseCurrency;
    private final currency.Currency targetCurrency;
    private final BigDecimal rate;
}
