package com.tdd.jpaboardfortdd.repository;

import com.tdd.jpaboardfortdd.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
