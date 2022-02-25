package com.tdd.jpaboardfortdd.controller;

import com.tdd.jpaboardfortdd.domain.Post;
import com.tdd.jpaboardfortdd.dto.PostCreateRequest;
import com.tdd.jpaboardfortdd.dto.PostUpdateRequest;
import com.tdd.jpaboardfortdd.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "users/{userId}/posts")
public class PostController {
    private final PostService postService;

    @PostMapping
    public ResponseEntity<Long> create(
            @PathVariable("userId") Long userId,
            @RequestBody PostCreateRequest postCreateRequest
    ) {
        return ResponseEntity.ok(postService.savePost(postCreateRequest, userId).getId());
    }

    @PutMapping("{postId}")
    public ResponseEntity<Long> update(
            @PathVariable("userId") Long userId,
            @PathVariable("postId") Long postId,
            @RequestBody PostUpdateRequest postUpdateRequest
    ) {
        return ResponseEntity.ok(postService.updatePost(postUpdateRequest, postId, userId).getId());
    }

    @GetMapping("{postId}")
    public ResponseEntity<Post> get(
            @PathVariable("postId") Long postId
    ) {
        return ResponseEntity.ok(postService.getPostById(postId));
    }

    @DeleteMapping("{postId}")
    public ResponseEntity<Long> delete(
            @PathVariable("userId") Long userId,
            @PathVariable("postId") Long postId
    ) {
        return ResponseEntity.ok(postService.deletePost(postId, userId));
    }
}
