package com.hashedin.dto;

import lombok.*;

@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class LoginResponseDto {

    private String token;
    private String name;
    private String role;

}
