package com.tdd.jpaboardfortdd.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentDeleteRequest {
    private Long userId;

    @Builder
    public CommentDeleteRequest(Long userId) {
        this.userId = userId;
    }
}