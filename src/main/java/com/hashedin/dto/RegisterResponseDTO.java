package com.hashedin.dto;
import lombok.*;

@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RegisterResponseDTO {

    private String token;
    private String name;
    private String message;}
