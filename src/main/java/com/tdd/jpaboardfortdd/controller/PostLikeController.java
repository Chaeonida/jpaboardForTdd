package com.tdd.jpaboardfortdd.controller;

import com.tdd.jpaboardfortdd.dto.PostLikesResponse;
import com.tdd.jpaboardfortdd.service.PostLikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "users/{userId}/posts/{postId}")
public class PostLikeController {
    private final PostLikesService postLikesService;

    @PostMapping
    public ResponseEntity<Long> save(
            @PathVariable("userId") Long userId,
            @PathVariable("postId") Long postId
    ) {
        return ResponseEntity.ok(postLikesService.savePostLike(userId, postId));
    }

    @GetMapping
    public ResponseEntity<List<PostLikesResponse>> get(
            @PathVariable("userId") Long userId,
            @PathVariable("postId") Long postId
    ) {
        return ResponseEntity.ok(postLikesService.getPostLikesByPostId(postId));
    }

    @DeleteMapping
    public ResponseEntity<Long> delete(
            @PathVariable("userId") Long userId,
            @PathVariable("postId") Long postId
    ) {
        return ResponseEntity.ok(postLikesService.deletePostLike(userId, postId));
    }
}
