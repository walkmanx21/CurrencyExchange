package rate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Currency;

@Getter
@Setter
@AllArgsConstructor
public class Rate {
    private Integer id;
    private String baseCurrencyCode;
    private Currency baseCurrency;
    private String targetCurrencyCode;
    private Currency targetCurrency;
    private BigDecimal rate;
}
