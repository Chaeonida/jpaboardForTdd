package com.tdd.jpaboardfortdd.service;

import com.tdd.jpaboardfortdd.domain.Post;
import com.tdd.jpaboardfortdd.domain.PostLikes;
import com.tdd.jpaboardfortdd.domain.User;
import com.tdd.jpaboardfortdd.dto.PostLikesResponse;
import com.tdd.jpaboardfortdd.repository.PostLikesRepository;
import com.tdd.jpaboardfortdd.repository.PostRepository;
import com.tdd.jpaboardfortdd.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class PostLikesService {
    final private PostRepository postRepository;
    final private UserRepository userRepository;
    final private PostLikesRepository postLikesRepository;

    public PostLikes save(Long userId, Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(IllegalArgumentException::new);
        User user = userRepository.findById(userId).orElseThrow(IllegalArgumentException::new);
        validDuplicatePostLikes(user, post);

        PostLikes postLikes = PostLikes.builder().user(user).post(post).build();
        return postLikesRepository.save(postLikes);
    }

    private void validDuplicatePostLikes(User user, Post post) {
        if (postLikesRepository.existsByUserAndPost(user, post)) delete(user.getId(), post.getId());
    }

    public List<PostLikesResponse> get(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(IllegalArgumentException::new);

        return postLikesRepository.getByPost(post).stream().map(PostLikes::toPostLikesResponse).collect(toList());
    }

    public Long delete(Long userId, long postId) {
        Post post = postRepository.findById(postId).orElseThrow(IllegalArgumentException::new);
        User user = userRepository.findById(userId).orElseThrow(IllegalArgumentException::new);
        validDuplicatePostLikes(user, post);

        return postLikesRepository.deleteByUserAndPost(user, post);
    }
}
