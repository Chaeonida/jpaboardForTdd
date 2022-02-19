package com.tdd.jpaboardfortdd.service;

import com.tdd.jpaboardfortdd.domain.User;
import com.tdd.jpaboardfortdd.dto.UserCreateRequest;
import com.tdd.jpaboardfortdd.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public Long save(UserCreateRequest userCreateRequest) {
        User user = User.builder()
                .age(userCreateRequest.getAge())
                .hobby(userCreateRequest.getHobby())
                .name(userCreateRequest.getName())
                .build();

        return userRepository.save(user).getId();
    }


}
