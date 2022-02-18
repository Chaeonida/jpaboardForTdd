package com.tdd.jpaboardfortdd.service;

import com.tdd.jpaboardfortdd.domain.Post;
import com.tdd.jpaboardfortdd.domain.User;
import com.tdd.jpaboardfortdd.dto.PostCreateRequest;
import com.tdd.jpaboardfortdd.repository.PostRepository;
import com.tdd.jpaboardfortdd.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public Post save(Post post) {
        return postRepository.save(post);
    }

    @Transactional
    public Post update(Long id, String title, String content) {
        Post findPost = postRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        findPost.updatePost(title, content);
        return findPost;
    }

    @Transactional(readOnly = true)
    public Post find(Long id)  {
        return postRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    @Transactional
    public Long delete(Long id) {
         postRepository.deleteById(id);

        return id;
    }
}
