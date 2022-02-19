package com.tdd.jpaboardfortdd.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserDetailResponse {
    private Long id;
    private String name;
    private String hobby;
    private int age;

    @Builder
    public UserDetailResponse(Long id, String name, String hobby, int age) {
        this.id = id;
        this.name = name;
        this.hobby = hobby;
        this.age = age;
    }
}
