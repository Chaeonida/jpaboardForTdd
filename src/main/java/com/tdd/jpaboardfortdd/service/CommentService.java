package com.tdd.jpaboardfortdd.service;

import com.tdd.jpaboardfortdd.domain.Comment;
import com.tdd.jpaboardfortdd.domain.Post;
import com.tdd.jpaboardfortdd.domain.User;
import com.tdd.jpaboardfortdd.dto.CommentCreateRequest;
import com.tdd.jpaboardfortdd.dto.CommentListResponse;
import com.tdd.jpaboardfortdd.dto.CommentUpdateRequest;
import com.tdd.jpaboardfortdd.repository.CommentRepository;
import com.tdd.jpaboardfortdd.repository.PostRepository;
import com.tdd.jpaboardfortdd.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class CommentService {
    private  final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public Comment saveComment(CommentCreateRequest commentRequest, Long postId, Long userId) {
        Post post = postRepository.findById(postId).orElseThrow(IllegalArgumentException::new);
        User user = userRepository.findById(userId).orElseThrow(IllegalArgumentException::new);
        Comment comment = Comment.builder()
                .user(user)
                .content(commentRequest.getContent())
                .post(post)
                .build();

        return commentRepository.save(comment);
    }

    @Transactional(readOnly = true)
    public List<CommentListResponse> getCommentByPostId(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(IllegalArgumentException::new);
        return commentRepository.findByPost(post).stream().map(Comment::toCommentListResponse).collect(toList());
    }

    @Transactional
    public Comment updateComment(CommentUpdateRequest commentUpdateRequest, Long commentId, Long userId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(IllegalArgumentException::new);
        Long compareUserId = comment.getUserId();

        validCommentWriter(userId, compareUserId);

        comment.update(commentUpdateRequest.getContent());
        return comment;
    }

    @Transactional
    public Long deleteComment(Long userId, Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(IllegalArgumentException::new);
        Long compareUserId = comment.getUserId();

        validCommentWriter(userId, compareUserId);

        commentRepository.delete(comment);
        return commentId;
    }

    private void validCommentWriter(final Long userId, final Long compareUserId) {
        if (!userId.equals(compareUserId)) {
            throw new IllegalArgumentException();
        }
    }
}
