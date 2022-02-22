package com.tdd.jpaboardfortdd.repository;

import com.tdd.jpaboardfortdd.domain.Post;
import com.tdd.jpaboardfortdd.domain.PostLikes;
import com.tdd.jpaboardfortdd.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikesRepository extends JpaRepository<PostLikes,Long> {
    boolean existsByUserAndPost(User user, Post post);
}
