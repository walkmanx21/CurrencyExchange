package dto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CurrencyResponseDto {
    private final Integer id;
    private final String code;
    private final String fullName;
    private final String sign;


}
