package entity;

import java.math.BigDecimal;
import java.util.Currency;

public class ExchangeRate {
    private Integer id;
    private Currency baseCurrency;
    private Currency targetCurrency;
    private BigDecimal rate;
}
