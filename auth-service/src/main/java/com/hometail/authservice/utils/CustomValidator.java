package com.hometail.authservice.utils;

import com.hometail.authservice.dto.SignupRequestDto;
import lombok.NoArgsConstructor;

import javax.validation.ValidationException;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@NoArgsConstructor
public class CustomValidator {

    private static final String EMAIL_PATTERN = "^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    private static final Pattern emailPattern = Pattern.compile(EMAIL_PATTERN);

    // length 8-20, digit or alphabet, symbols 조합 필요
    private static final String PASSWORD_PATTERN = "^(?=.*[0-9A-z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
    private static final Pattern passwordPattern = Pattern.compile(PASSWORD_PATTERN);
    private @NotBlank(message = "이메일은 필수 입력 항목입니다.") @Email(message = "이메일 형식에 맞지 않습니다.") String email;

    public boolean validSignupRequestDto(SignupRequestDto dto) throws ValidationException {

        ArrayList<String> results = new ArrayList<>();

        if (dto.getEmail() == null) {
            results.add("이메일은 필수 입력 사항입니다.");
        } else if (!emailPattern.matcher(dto.getEmail()).matches()) {
            results.add("이메일 형식에 맞지 않습니다.");
        }

        if (dto.getPassword() == null) {
            results.add("비밀번호는 필수 입력 사항입니다.");
        } else if (!passwordPattern.matcher(dto.getPassword()).matches()) {
                results.add("비밀번호는 알파벳 또는 숫자, 특수기호가 적어도 1개 이상 포함된 8~20글자로 이루어져야 합니다.");
        }

        if (dto.getNickname() == null) {
            results.add("닉네임은 필수 입력 사항입니다.");
        }

        if (results.size() != 0) {
            throw new ValidationException(results.stream().map(o -> o.toString()).collect(Collectors.joining("\n")));
        }

        return true;
    }
}
