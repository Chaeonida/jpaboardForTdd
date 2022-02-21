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
public class PostController {
    private final PostService postService;

    @PostMapping("/posts")
    public ResponseEntity<Long> create(
            @RequestBody PostCreateRequest postCreateRequest
    ) {
        return ResponseEntity.ok(postService.save(postCreateRequest).getId());
    }

    @PutMapping("/posts/{id}")
    public ResponseEntity<Long> update(
            @PathVariable("id") Long postId, @RequestBody PostUpdateRequest postUpdateRequest
    ) {
        return ResponseEntity.ok(postService.update(postUpdateRequest,postId).getId());
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<Post> get(
            @PathVariable("id") Long postId
    ) {
        return ResponseEntity.ok(postService.find(postId));
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<Long> delete(
            @PathVariable("id") Long postId
    ) {
        return ResponseEntity.ok(postService.delete(postId));
    }



}
