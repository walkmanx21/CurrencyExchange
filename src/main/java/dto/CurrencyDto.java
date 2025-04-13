package dto;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class CurrencyDto {
    private final String code;
    private final String fullName;
    private final String sign;

    public CurrencyDto(String code, String fullName, String sign) {
        this.code = code;
        this.fullName = fullName;
        this.sign = sign;
    }

    public CurrencyDto(String code) {
        this.code = code;
        this.fullName = null;
        this.sign = null;
    }


}
