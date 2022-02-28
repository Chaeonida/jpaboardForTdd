package com.tdd.jpaboardfortdd.controller;

import com.tdd.jpaboardfortdd.domain.User;
import com.tdd.jpaboardfortdd.dto.PostLikesResponse;
import com.tdd.jpaboardfortdd.service.PostLikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "posts/{postId}")
public class PostLikeController {
    private final PostLikesService postLikesService;

    @PostMapping
    public ResponseEntity<Long> save(
            @AuthenticationPrincipal User user,
            @PathVariable("postId") Long postId
    ) {
        return ResponseEntity.ok(postLikesService.savePostLike(user.getId(), postId));
    }

    @GetMapping
    public ResponseEntity<List<PostLikesResponse>> get(
            @PathVariable("postId") Long postId
    ) {
        return ResponseEntity.ok(postLikesService.getPostLikesByPostId(postId));
    }

    @DeleteMapping
    public ResponseEntity<Long> delete(
            @AuthenticationPrincipal User user,
            @PathVariable("postId") Long postId
    ) {
        return ResponseEntity.ok(postLikesService.deletePostLike(user.getId(), postId));
    }
}
