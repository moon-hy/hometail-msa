package com.hometail.authservice.dto;

import com.hometail.authservice.domain.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
public class LoginRequestDto {

    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

    public Account toEntity() {

        return Account.builder()
                .email(email)
                .password(password)
                .build();
    }
}
