package com.tdd.jpaboardfortdd.repository;

import com.tdd.jpaboardfortdd.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
