package com.hometail.authservice.domain;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Setter
    private String password;

    @Column(nullable = false)
    private String nickname;

    @Builder.Default
    private Role role = Role.ROLE_USER;

    @Builder.Default
    private boolean activated = false;
}
