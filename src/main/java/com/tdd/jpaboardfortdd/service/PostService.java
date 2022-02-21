package com.tdd.jpaboardfortdd.service;

import com.tdd.jpaboardfortdd.domain.Post;
import com.tdd.jpaboardfortdd.domain.User;
import com.tdd.jpaboardfortdd.dto.PostCreateRequest;
import com.tdd.jpaboardfortdd.dto.PostUpdateRequest;
import com.tdd.jpaboardfortdd.repository.PostRepository;
import com.tdd.jpaboardfortdd.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.persistence.PostUpdate;

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
    public Post update(PostUpdateRequest postUpdateRequest, Long PostId) {
        Post findPost = postRepository.findById(PostId).orElseThrow(IllegalArgumentException::new);

        if (!userValid(
                findPost.getUser().getId(),
                postUpdateRequest.getUserId()
        )) {
            findPost.updatePost(postUpdateRequest.getTitle(), postUpdateRequest.getContent());
        }
        return findPost;
    }

    @Transactional(readOnly = true)
    public Post find(Long id) {
        return postRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    @Transactional
    public Long delete(Long id) {
        postRepository.deleteById(id);

        return id;
    }

    public boolean userValid(final Long userId, final Long compareUserId) {
        if (!userId.equals(compareUserId)) {
            throw new IllegalArgumentException();
        }
        return false;
    }
}
