package com.tdd.jpaboardfortdd.service;

import com.tdd.jpaboardfortdd.domain.Comment;
import com.tdd.jpaboardfortdd.domain.Post;
import com.tdd.jpaboardfortdd.domain.User;
import com.tdd.jpaboardfortdd.dto.CommentCreateRequest;
import com.tdd.jpaboardfortdd.dto.CommentDeleteRequest;
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
    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private UserRepository userRepository;

    @Transactional
    public Comment save(CommentCreateRequest commentRequest, Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(IllegalArgumentException::new);
        User user = userRepository.findById(commentRequest.getUserId()).orElseThrow(IllegalArgumentException::new);
        Comment comment = Comment.builder()
                .user(user)
                .content(commentRequest.getContent())
                .post(post)
                .build();

        return commentRepository.save(comment);
    }

    @Transactional(readOnly = true)
    public List<CommentListResponse> get(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(IllegalArgumentException::new);
        return commentRepository.findByPost(post).stream().map(Comment::toCommentListResponse).collect(toList());
    }

    public Comment update(CommentUpdateRequest commentUpdateRequest, Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(IllegalArgumentException::new);
        Long userId = commentUpdateRequest.getUserId();
        Long compareUserId = comment.getUser().getId();
        commentWriterValid(userId, compareUserId);
        comment.update(commentUpdateRequest.getContent());

        return comment;
    }

    public Long delete(CommentDeleteRequest commentDeleteRequest, long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(IllegalArgumentException::new);
        Long userId = commentDeleteRequest.getUserId();
        Long compareUserId = comment.getUser().getId();
        commentWriterValid(userId, compareUserId);

        commentRepository.delete(comment);
        return commentId;
    }

    public void commentWriterValid(final Long userId, final Long compareUserId) {
        if (!userId.equals(compareUserId)) {
            throw new IllegalArgumentException();
        }
    }
}
