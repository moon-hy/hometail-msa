package com.hometail.userservice.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileResponseDto {

    private Long id;
    private String email;
    private String role;
    private String nickname;
    private boolean activated;
}
