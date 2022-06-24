package com.hometail.authservice.service;

import com.hometail.authservice.domain.Account;
import com.hometail.authservice.domain.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public String addAccount(Account account) {
        accountRepository.save(account);
        return "{SIGNED_UP}";
    }

    public String login(Account account) {

        Account targetAccount = accountRepository.findByEmail(account.getEmail());

        if (targetAccount.isValidPassword(passwordEncoder, account.getPassword())) {

            return "{LOGGED_IN}";
        } else {
            throw new ValidationException("비밀번호가 일치하지 않습니다.");
        }
    }

    public void getJwt() {

    }
}
