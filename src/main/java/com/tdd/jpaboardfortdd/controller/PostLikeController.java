package com.tdd.jpaboardfortdd.controller;

import com.tdd.jpaboardfortdd.dto.PostLikesResponse;
import com.tdd.jpaboardfortdd.service.PostLikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "users/{id}/posts/{id}")
public class PostLikeController {
    private final PostLikesService postLikesService;

    @PostMapping
    public ResponseEntity<Long> save(
            @PathVariable("id") Long userId,
            @PathVariable("id") Long postId
    ) {
        return ResponseEntity.ok(postLikesService.savePostLike(userId,postId).getId());
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<PostLikesResponse>> get(
            @PathVariable("id") Long userId,
            @PathVariable("id") Long postId
    ) {
        return ResponseEntity.ok(postLikesService.getPostLikesByPostId(postId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(
            @PathVariable("id") Long userId,
            @PathVariable("id") Long postId
    ) {
        return ResponseEntity.ok(postLikesService.deletePostLike(userId,postId));
    }
}
