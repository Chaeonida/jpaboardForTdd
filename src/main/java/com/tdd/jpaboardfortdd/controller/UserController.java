package com.tdd.jpaboardfortdd.controller;

import com.tdd.jpaboardfortdd.domain.User;
import com.tdd.jpaboardfortdd.dto.UserCreateRequest;
import com.tdd.jpaboardfortdd.dto.UserDetailResponse;
import com.tdd.jpaboardfortdd.dto.UserSignInRequest;
import com.tdd.jpaboardfortdd.dto.UserUpdateRequest;
import com.tdd.jpaboardfortdd.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원가입
    @PostMapping("/join")
    public ResponseEntity<Long> join(@RequestBody UserCreateRequest userCreateRequest) {
        return ResponseEntity.ok(userService.saveUser(userCreateRequest));
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserSignInRequest userSignInRequest) {
        return ResponseEntity.ok(userService.logIn(userSignInRequest));
    }

    @PutMapping("users")
    public ResponseEntity<Long> updateUser(
            @AuthenticationPrincipal User user, @RequestBody UserUpdateRequest userUpdateRequest
    ) {
        return ResponseEntity.ok(userService.updateUser(userUpdateRequest, user.getId()));
    }

    @GetMapping("users")
    public ResponseEntity<UserDetailResponse> get(
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(userService.getUserByUserId(user.getId()));
    }

    @DeleteMapping("users")
    public ResponseEntity<Long> delete(
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(userService.deleteUser(user.getId()));
    }
}
