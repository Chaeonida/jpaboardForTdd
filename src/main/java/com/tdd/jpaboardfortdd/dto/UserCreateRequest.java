package com.tdd.jpaboardfortdd.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserCreateRequest {
    private String email;
    private String password;

    @Builder
    public UserCreateRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
