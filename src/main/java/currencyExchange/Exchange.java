package currencyExchange;

import currency.Currency;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class Exchange {
    private String baseCurrencyCode;
    private Currency baseCurrency;
    private String targetCurrencyCode;
    private Currency targetCurrency;
    private BigDecimal rate;
    private BigDecimal amount;
    private BigDecimal convertedAmount;

    public Exchange (String baseCurrencyCode, String targetCurrencyCode, BigDecimal amount) {
        this.baseCurrencyCode = baseCurrencyCode;
        this.baseCurrency = null;
        this.targetCurrencyCode = targetCurrencyCode;
        this.targetCurrency = null;
        this.rate = null;
        this.amount = amount;
        this.convertedAmount = null;
    }
}
