package com.hometail.authservice.service;

import com.hometail.authservice.domain.Account;
import com.hometail.authservice.exception.InvalidRequestException;
import com.hometail.authservice.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AccountService {

    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;

    public Account addAccount(Account account) {

        if (accountRepository.existsByEmail(account.getEmail())) {
            throw InvalidRequestException.DuplicatedEmail;
        }
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        Account savedAccount = accountRepository.save(account);
        return savedAccount;
    }

    public Account getAccount(Account account) {

        Account savedAccount = accountRepository.findByEmail(account.getEmail())
                .orElseThrow(() -> InvalidRequestException.NotExistsEmail);

        if (!passwordEncoder.matches(account.getPassword(), savedAccount.getPassword())) {
            throw InvalidRequestException.InvalidPassword;
        }

        return savedAccount;
    }

    public Account getAccountById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> InvalidRequestException.NotExistsId);
    }
}
