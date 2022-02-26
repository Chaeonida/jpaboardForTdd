package com.tdd.jpaboardfortdd.dto;

import lombok.Getter;

@Getter
public class UserSignInRequest {

    private String email;

    private String password;

    protected UserSignInRequest() {
    }

    public UserSignInRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}

