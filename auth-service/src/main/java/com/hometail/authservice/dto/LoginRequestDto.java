package com.hometail.authservice.dto;

import com.hometail.authservice.domain.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
public class LoginRequestDto {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

    public Account toEntity() {
        return Account.builder()
                .email(email)
                .password(password).build();
    }
}
