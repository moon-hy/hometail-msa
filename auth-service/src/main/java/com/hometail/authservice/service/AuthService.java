package com.hometail.authservice.service;

import com.hometail.authservice.domain.Account;
import com.hometail.authservice.domain.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final AccountRepository accountRepository;

    public Account addAccount(Account account) {

        return accountRepository.save(account);
    }

    public void getJwt() {

    }
}
