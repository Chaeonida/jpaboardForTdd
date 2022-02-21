package com.tdd.jpaboardfortdd.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostDeleteRequest {
    private Long userId;

    @Builder
    public PostDeleteRequest(Long userId) {
        this.userId = userId;
    }
}
