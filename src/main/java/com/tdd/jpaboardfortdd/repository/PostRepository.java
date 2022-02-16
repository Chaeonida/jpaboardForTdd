package com.tdd.jpaboardfortdd.repository;

import com.tdd.jpaboardfortdd.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
