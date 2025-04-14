package dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class CurrencyRequestDto {
    private final String code;
    private final String fullName;
    private final String sign;

    public CurrencyRequestDto(String code) {
        this.code = code;
        this.fullName = null;
        this.sign = null;
    }
}
