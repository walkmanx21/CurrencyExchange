package dto;

import java.math.BigDecimal;
import java.util.Currency;

/**
 * Integer	id	1
 * Currency	baseCurrency	USD
 * Currency	targetCurrency	USD
 * Integer	rate	84,44
 */

public class ExchangeRateResponseDto {
    private Integer id;
    private Currency baseCurrency;
    private Currency targetCurrency;
    private BigDecimal rate;
}
