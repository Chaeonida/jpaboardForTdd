package com.tdd.jpaboardfortdd.repository;

import com.tdd.jpaboardfortdd.domain.Post;
import com.tdd.jpaboardfortdd.domain.PostLikes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostLikesRepository extends JpaRepository<PostLikes, Long> {
    boolean existsByUserIdAndPostId(Long userId, Long postId);

    Long deleteByUserIdAndPostId(Long userId, Long postId);

    List<PostLikes> getByPost(Post post);
}
