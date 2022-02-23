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

    public PostLikes savePostLike(Long userId, Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(IllegalArgumentException::new);
        User user = userRepository.findById(userId).orElseThrow(IllegalArgumentException::new);
        if (postLikesRepository.existsByUserIdAndPostId(userId, postId)) {
            deletePostLike(userId, postId);
        }

        PostLikes postLikes = PostLikes.builder().user(user).post(post).build();
        return postLikesRepository.save(postLikes);
    }

    public List<PostLikesResponse> getPostLikesByPostId(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(IllegalArgumentException::new);

        return postLikesRepository.getByPost(post).stream().map(PostLikes::toPostLikesResponse).collect(toList());
    }

    public Long deletePostLike(Long userId, long postId) {

        return postLikesRepository.deleteByUserIdAndPostId(userId, postId);
    }
}
