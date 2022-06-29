package com.hometail.userservice.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Profile {

    @Id
    private Long accountId;

    private String nickname;
}
