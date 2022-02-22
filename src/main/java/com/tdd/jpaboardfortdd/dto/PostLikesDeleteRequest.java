package com.tdd.jpaboardfortdd.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostLikesDeleteRequest {
    private Long userId;
    @Builder
    public PostLikesDeleteRequest(Long userId) {
        this.userId = userId;
    }
}
