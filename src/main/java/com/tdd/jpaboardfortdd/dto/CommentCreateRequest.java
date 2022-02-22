package com.tdd.jpaboardfortdd.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentCreateRequest {
    private String content;
    private Long userId;

    @Builder
    public CommentCreateRequest(String content, Long userId) {
        this.content = content;
        this.userId = userId;
    }
}
