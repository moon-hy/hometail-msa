package com.hometail.authservice.dto;

import com.hometail.authservice.domain.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@AllArgsConstructor
public class SignupRequestDto {

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "이메일 형식에 맞지 않습니다.")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp="^(?=.*[0-9A-z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$",
            message = "비밀번호는 알파벳 또는 숫자, 특수기호가 적어도 1개 이상 포함된 8~20글자로 이루어져야 합니다.")
    private String password;

    @NotBlank(message = "닉네임을 입력해주세요.")
    private String nickname;

    public Account toEntity(PasswordEncoder passwordEncoder) {

        return Account.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .nickname(nickname)
                .build();
    }
}
