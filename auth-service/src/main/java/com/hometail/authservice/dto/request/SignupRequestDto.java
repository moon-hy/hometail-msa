package com.hometail.authservice.dto.request;

import com.hometail.authservice.domain.Account;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class SignupRequestDto {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[A-z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$")
    private String password;

    @NotBlank
    private String nickname;

    public Account toEntity() {
        return Account.builder()
                .email(email)
                .password(password).build();
    }
}
