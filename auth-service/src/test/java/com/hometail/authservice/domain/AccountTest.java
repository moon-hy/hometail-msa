package com.hometail.authservice.domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AccountTest {

    @Autowired
    private AccountRepository accountRepository;

    @AfterEach
    public void cleanup() {

        accountRepository.deleteAll();
    }

    @Test
    @DisplayName("Account 저장 및 불러오기")
    public void account_save_and_load() {

        //given
        String email = "test@test.com";
        String nickname = "test";

        accountRepository.save(Account.builder()
                .email(email)
                .nickname(nickname)
                .build());

        //when
        Account account = accountRepository.findAll().get(0);

        //then
        assert (account.getEmail().equals(email));
        assert (account.getNickname().equals(nickname));
        assert (account.getIsActivate() == true);
        assert (account.getRole().equals("USER"));
    }
}
