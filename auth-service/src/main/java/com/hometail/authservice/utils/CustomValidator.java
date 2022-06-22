package com.hometail.authservice.utils;

import com.hometail.authservice.dto.SignupDto;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

public class CustomValidator {
    
    public boolean validSignupDto(SignupDto dto) throws MethodArgumentNotValidException {

        BeanPropertyBindingResult result = new BeanPropertyBindingResult(dto, "SignupDto");

        System.out.println(dto.getPassword());
        System.out.println(dto.getPassword() == dto.getPasswordConfirm());
        if (dto.getEmail() == null) {
            result.addError(new ObjectError("email", "이메일은 필수 입력 사항입니다."));
        }
        if (dto.getPassword() == null) {
            result.addError(new ObjectError("password", "비밀번호는 필수 입력 사항입니다."));
        }
        if (dto.getNickname() == null) {
            result.addError(new ObjectError("nickname", "닉네임은 필수 입력 사항입니다."));
        }
        if (!dto.getPassword().equals(dto.getPasswordConfirm())) {
            result.addError(new ObjectError("password", "비밀번호가 일치하지 않습니다."));
        }
        if (result.getErrorCount() != 0) {
            throw new MethodArgumentNotValidException(null, result);
        }

        return true;
    }
}
