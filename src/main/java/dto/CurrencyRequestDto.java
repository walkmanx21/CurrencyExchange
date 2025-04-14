package dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CurrencyRequestDto {
    private final String code;
    private final String fullName;
    private final String sign;

    public CurrencyRequestDto(String code, String fullName, String sign) {
        this.code = code;
        this.fullName = fullName;
        this.sign = sign;
    }

    public CurrencyRequestDto(String code) {
        this.code = code;
        this.fullName = null;
        this.sign = null;
    }


}
