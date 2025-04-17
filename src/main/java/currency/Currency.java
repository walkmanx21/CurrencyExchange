package currency;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Currency {
    private Integer id;
    private String code;
    private String fullName;
    private String sign;

    public Currency (String code, String fullName, String sign) {
        this.id = null;
        this.code = code;
        this.fullName = fullName;
        this.sign = sign;
    }
}
