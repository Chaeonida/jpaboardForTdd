package com.tdd.jpaboardfortdd.controller;

import com.tdd.jpaboardfortdd.domain.User;
import com.tdd.jpaboardfortdd.dto.CommentCreateRequest;
import com.tdd.jpaboardfortdd.dto.CommentListResponse;
import com.tdd.jpaboardfortdd.dto.CommentUpdateRequest;
import com.tdd.jpaboardfortdd.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "posts")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("{postId}")
    public ResponseEntity<Long> createComment(
            @AuthenticationPrincipal User user,
            @PathVariable("postId") Long postId,
            @RequestBody CommentCreateRequest commentCreateRequest
    ) {
        return ResponseEntity.ok(commentService.saveComment(commentCreateRequest, postId, user.getId()).getId());
    }

    @PutMapping("comments/{commentId}")
    public ResponseEntity<Long> updateComment(
            @AuthenticationPrincipal User user,
            @PathVariable("commentId") Long commentId,
            @RequestBody CommentUpdateRequest commentUpdateRequest
    ) {
        return ResponseEntity.ok(commentService.updateComment(commentUpdateRequest, commentId, user.getId()).getId());
    }

    @GetMapping("{postId}")
    public ResponseEntity<List<CommentListResponse>> get(
            @PathVariable("postId") Long postId
    ) {
        return ResponseEntity.ok(commentService.getCommentByPostId(postId));
    }

    @DeleteMapping("comments/{commentId}")
    public ResponseEntity<Long> delete(
            @AuthenticationPrincipal User user,
            @PathVariable("commentId") Long commentId
    ) {
        return ResponseEntity.ok(commentService.deleteComment(user.getId(), commentId));
    }
}
