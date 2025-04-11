package entity;

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
}
