package com.tdd.jpaboardfortdd.repository;

import com.tdd.jpaboardfortdd.domain.Post;
import com.tdd.jpaboardfortdd.domain.PostLikes;
import com.tdd.jpaboardfortdd.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostLikesRepository extends JpaRepository<PostLikes,Long> {
    boolean existsByUserAndPost(User user, Post post);
    Long deleteByUserAndPost(User user, Post post);
    List<PostLikes> getByPost(Post post);
}
