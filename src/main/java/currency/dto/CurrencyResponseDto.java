package currency.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class CurrencyResponseDto {
    private final Integer id;
    private final String code;
    private final String fullName;
    private final String sign;

}
