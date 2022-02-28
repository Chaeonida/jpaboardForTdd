package com.tdd.jpaboardfortdd.controller;

import com.tdd.jpaboardfortdd.domain.Post;
import com.tdd.jpaboardfortdd.domain.User;
import com.tdd.jpaboardfortdd.dto.PostCreateRequest;
import com.tdd.jpaboardfortdd.dto.PostUpdateRequest;
import com.tdd.jpaboardfortdd.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "posts")
public class PostController {
    private final PostService postService;

    @PostMapping
    public ResponseEntity<Long> create(
            @AuthenticationPrincipal User user,
            @RequestBody PostCreateRequest postCreateRequest
    ) {
        return ResponseEntity.ok(postService.savePost(postCreateRequest, user.getId()).getId());
    }

    @PutMapping("{postId}")
    public ResponseEntity<Long> update(
            @AuthenticationPrincipal User user,
            @PathVariable("postId") Long postId,
            @RequestBody PostUpdateRequest postUpdateRequest
    ) {
        return ResponseEntity.ok(postService.updatePost(postUpdateRequest, postId, user.getId()).getId());
    }

    @GetMapping("{postId}")
    public ResponseEntity<Post> get(
            @PathVariable("postId") Long postId
    ) {
        return ResponseEntity.ok(postService.getPostById(postId));
    }

    @DeleteMapping("{postId}")
    public ResponseEntity<Long> delete(
            @AuthenticationPrincipal User user,
            @PathVariable("postId") Long postId
    ) {
        return ResponseEntity.ok(postService.deletePost(postId, user.getId()));
    }
}
