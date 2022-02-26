package com.tdd.jpaboardfortdd.service;

import com.tdd.jpaboardfortdd.domain.User;
import com.tdd.jpaboardfortdd.dto.UserCreateRequest;
import com.tdd.jpaboardfortdd.dto.UserDetailResponse;
import com.tdd.jpaboardfortdd.dto.UserSignInRequest;
import com.tdd.jpaboardfortdd.dto.UserUpdateRequest;
import com.tdd.jpaboardfortdd.repository.UserRepository;
import com.tdd.jpaboardfortdd.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public Long saveUser(UserCreateRequest userCreateRequest) {
        return userRepository.save(User.builder()
                .email(userCreateRequest.getEmail())
                .password(passwordEncoder.encode(userCreateRequest.getPassword()))
                .roles(Collections.singletonList("ROLE_USER")) // 최초 가입시 USER 로 설정
                .build()).getId();
    }

    public String logIn(UserSignInRequest userSignInRequest) {
        User member = userRepository.findByEmail(userSignInRequest.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 E-MAIL 입니다."));
        if (!passwordEncoder.matches(userSignInRequest.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }
        return jwtTokenProvider.createToken(member.getUsername(), member.getRoles());
    }

    @Transactional
    public Long updateUser(UserUpdateRequest userUpdateRequest, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(IllegalArgumentException::new);
        user.update(userUpdateRequest.getName(), userUpdateRequest.getHobby(), userUpdateRequest.getAge());

        return user.getId();
    }

    public UserDetailResponse getUserByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(IllegalArgumentException::new);

        return UserDetailResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .age(user.getAge())
                .hobby(user.getHobby())
                .build();
    }

    public Long deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(IllegalAccessError::new);
        userRepository.delete(user);

        return user.getId();
    }
}
