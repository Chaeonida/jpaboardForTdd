package com.tdd.jpaboardfortdd.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentUpdateRequest {
    private Long commentId;
    private String content;
    private Long userId;

    @Builder
    public CommentUpdateRequest(Long commentId, String content, Long userId) {
        this.commentId = commentId;
        this.content = content;
        this.userId = userId;
    }
}
