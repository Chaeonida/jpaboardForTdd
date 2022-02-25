package com.tdd.jpaboardfortdd.controller;

import com.tdd.jpaboardfortdd.dto.CommentCreateRequest;
import com.tdd.jpaboardfortdd.dto.CommentListResponse;
import com.tdd.jpaboardfortdd.dto.CommentUpdateRequest;
import com.tdd.jpaboardfortdd.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "users/{userId}/posts/{postId}")
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Long> createComment(
            @PathVariable("userId") Long userId,
            @PathVariable("postId") Long postId,
            @RequestBody CommentCreateRequest commentCreateRequest
    ) {
        return ResponseEntity.ok(commentService.saveComment(commentCreateRequest, postId, userId).getId());
    }

    @PutMapping("{commentId}")
    public ResponseEntity<Long> updateComment(
            @PathVariable("userId") Long userId,
            @PathVariable("postId") Long postId,
            @PathVariable("commentId") Long commentId,
            @RequestBody CommentUpdateRequest commentUpdateRequest
    ) {
        return ResponseEntity.ok(commentService.updateComment(commentUpdateRequest, commentId, userId).getId());
    }

    @GetMapping("{commentId}")
    public ResponseEntity<List<CommentListResponse>> get(
            @PathVariable("userId") Long userId,
            @PathVariable("postId") Long postId,
            @PathVariable("commentId") Long commentId
    ) {
        return ResponseEntity.ok(commentService.getCommentByPostId(postId));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Long> delete(
            @PathVariable("userId") Long userId,
            @PathVariable("postId") Long postId,
            @PathVariable("commentId") Long commentId
    ) {
        return ResponseEntity.ok(commentService.deleteComment(userId, commentId));
    }
}
