package com.hometail.gatewayservice.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenUser {

    private Long id;
    private String role;
}
