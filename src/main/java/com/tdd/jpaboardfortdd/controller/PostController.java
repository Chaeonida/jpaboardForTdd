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
@RequestMapping(value = "users/{id}/posts")
public class PostController {
    private final PostService postService;

    @PostMapping
    public ResponseEntity<Long> create(
            @PathVariable("id") Long userId,
            @RequestBody PostCreateRequest postCreateRequest
    ) {
        return ResponseEntity.ok(postService.savePost(postCreateRequest, userId).getId());
    }

    @PutMapping("{id}")
    public ResponseEntity<Long> update(
            @PathVariable("id") Long userId,
            @PathVariable("id") Long postId,
            @RequestBody PostUpdateRequest postUpdateRequest
    ) {
        return ResponseEntity.ok(postService.updatePost(postUpdateRequest, postId, userId).getId());
    }

    @GetMapping("{id}")
    public ResponseEntity<Post> get(
            @PathVariable("id") Long postId
    ) {
        return ResponseEntity.ok(postService.getPostById(postId));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Long> delete(
            @PathVariable("id") Long userId,
            @PathVariable("id") Long postId
    ) {
        return ResponseEntity.ok(postService.deletePost(postId, userId));
    }
}
