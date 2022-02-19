package com.tdd.jpaboardfortdd.service;

import com.tdd.jpaboardfortdd.domain.User;
import com.tdd.jpaboardfortdd.dto.UserCreateRequest;
import com.tdd.jpaboardfortdd.dto.UserDetailResponse;
import com.tdd.jpaboardfortdd.dto.UserUpdateRequest;
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


    @Transactional
    public Long update(UserUpdateRequest userUpdateRequest,Long userId) {
        User user = userRepository.findById(userId).orElseThrow(IllegalAccessError::new);
        user.update(userUpdateRequest.getName(), userUpdateRequest.getHobby(), userUpdateRequest.getAge());

        return user.getId();
    }

    public UserDetailResponse get(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(IllegalAccessError::new);

        return UserDetailResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .age(user.getAge())
                .hobby(user.getHobby())
                .build();
    }

    public Long delete(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(IllegalAccessError::new);
        userRepository.delete(user);

        return user.getId();
    }
}
