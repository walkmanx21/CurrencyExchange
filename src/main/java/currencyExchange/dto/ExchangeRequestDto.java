package currencyExchange.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
@RequiredArgsConstructor
public class ExchangeRequestDto {
    private final String baseCurrencyCode;
    private final String targetCurrencyCode;
    private final BigDecimal amount;

}
