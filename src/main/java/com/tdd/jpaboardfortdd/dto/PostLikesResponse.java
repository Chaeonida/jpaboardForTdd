package com.tdd.jpaboardfortdd.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostLikesResponse {
    private Long postLikesId;
    private Long userId;

    @Builder
    public PostLikesResponse(Long postLikesId, Long userId) {
        this.postLikesId = postLikesId;
        this.userId = userId;
    }
}
