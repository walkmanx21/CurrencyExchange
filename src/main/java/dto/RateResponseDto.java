package dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Currency;

@Getter
@Setter
@RequiredArgsConstructor
public class RateResponseDto {
    private final Integer id;
    private final Currency baseCurrency;
    private final Currency targetCurrency;
    private final BigDecimal rate;
}
