package com.tdd.jpaboardfortdd.service;

import com.tdd.jpaboardfortdd.domain.Post;
import com.tdd.jpaboardfortdd.domain.PostLikes;
import com.tdd.jpaboardfortdd.domain.User;
import com.tdd.jpaboardfortdd.dto.PostLikesCreateRequest;
import com.tdd.jpaboardfortdd.repository.PostLikesRepository;
import com.tdd.jpaboardfortdd.repository.PostRepository;
import com.tdd.jpaboardfortdd.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostLikesService {
    private PostRepository postRepository;
    private UserRepository userRepository;
    private PostLikesRepository postLikesRepository;

    public PostLikes save(PostLikesCreateRequest postLikesCreateRequest, Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(IllegalArgumentException::new);
        User user = userRepository.findById(postLikesCreateRequest.getUserId()).orElseThrow(IllegalArgumentException::new);

        isDuplicateCommentLikes(user, post);
        PostLikes postLikes = PostLikes.builder().user(user).post(post).build();
        return postLikesRepository.save(postLikes);
    }

    private void isDuplicateCommentLikes(User user, Post post) {
        if (postLikesRepository.existsByUserAndPost(user, post)) throw new IllegalArgumentException();
    }
}
