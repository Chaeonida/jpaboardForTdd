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
@RequestMapping(value = "users/{id}/posts/{id}")
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Long> createComment(
            @PathVariable("id") Long userId,
            @PathVariable("id") Long postId,
            @RequestBody CommentCreateRequest commentCreateRequest
    ) {
        return ResponseEntity.ok(commentService.save(commentCreateRequest, postId, userId).getId());
    }

    @PutMapping("{id}")
    public ResponseEntity<Long> updateComment(
            @PathVariable("id") Long userId,
            @PathVariable("id") Long postId,
            @PathVariable("id") Long commentId,
            @RequestBody CommentUpdateRequest commentUpdateRequest
    ) {
        return ResponseEntity.ok(commentService.update(commentUpdateRequest, commentId, userId).getId());
    }

    @GetMapping("{id}")
    public ResponseEntity<List<CommentListResponse>> get(
            @PathVariable("id") Long userId,
            @PathVariable("id") Long postId,
            @PathVariable("id") Long commentId
    ) {
        return ResponseEntity.ok(commentService.get(postId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(
            @PathVariable("id") Long userId,
            @PathVariable("id") Long postId,
            @PathVariable("id") Long commentId
    ) {
        return ResponseEntity.ok(commentService.delete(userId, commentId));
    }
}
