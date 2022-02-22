package com.tdd.jpaboardfortdd.service;

import com.tdd.jpaboardfortdd.domain.Post;
import com.tdd.jpaboardfortdd.domain.User;
import com.tdd.jpaboardfortdd.dto.PostCreateRequest;
import com.tdd.jpaboardfortdd.dto.PostDeleteRequest;
import com.tdd.jpaboardfortdd.dto.PostUpdateRequest;
import com.tdd.jpaboardfortdd.repository.PostRepository;
import com.tdd.jpaboardfortdd.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public Post save(PostCreateRequest postCreateRequest) {
        User user = userRepository.findById(postCreateRequest.getUserId()).orElseThrow(IllegalArgumentException::new);
        Post post = Post.builder()
                .title(postCreateRequest.getTitle())
                .content(postCreateRequest.getContent())
                .user(user)
                .build();

        return postRepository.save(post);
    }

    @Transactional
    public Post update(PostUpdateRequest postUpdateRequest, Long postId) {
        Post findPost = postRepository.findById(postId).orElseThrow(IllegalArgumentException::new);
        postWriterValid(findPost.getUser().getId(), postUpdateRequest.getUserId());
        findPost.updatePost(postUpdateRequest.getTitle(), postUpdateRequest.getContent());

        return findPost;
    }

    @Transactional(readOnly = true)
    public Post find(Long id) {
        return postRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    @Transactional
    public Long delete(Long id, PostDeleteRequest postDeleteRequest) {
        postWriterValid(id, postDeleteRequest.getUserId());
        postRepository.deleteById(id);

        return id;
    }

    public void postWriterValid(final Long userId, final Long compareUserId) {
        if (!userId.equals(compareUserId)) {
            throw new IllegalArgumentException();
        }
    }
}
