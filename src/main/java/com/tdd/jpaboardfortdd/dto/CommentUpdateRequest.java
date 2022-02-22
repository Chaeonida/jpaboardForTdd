package com.tdd.jpaboardfortdd.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentUpdateRequest {
    private String content;
    private Long userId;

    @Builder
    public CommentUpdateRequest(String content, Long userId) {
        this.content = content;
        this.userId = userId;
    }
}
