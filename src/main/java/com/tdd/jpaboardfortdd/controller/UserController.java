package com.tdd.jpaboardfortdd.controller;

import com.tdd.jpaboardfortdd.dto.UserCreateRequest;
import com.tdd.jpaboardfortdd.dto.UserDetailResponse;
import com.tdd.jpaboardfortdd.dto.UserSignInRequest;
import com.tdd.jpaboardfortdd.dto.UserUpdateRequest;
import com.tdd.jpaboardfortdd.security.JwtAuthentication;
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

    @PutMapping("users/{id}")
    public ResponseEntity<Long> updateUser(
            @AuthenticationPrincipal JwtAuthentication token, @RequestBody UserUpdateRequest userUpdateRequest
    ) {
        return ResponseEntity.ok(userService.updateUser(userUpdateRequest, token.getId()));
    }

    @GetMapping("users/{id}")
    public ResponseEntity<UserDetailResponse> get(
            @PathVariable("id") Long userId
    ) {
        return ResponseEntity.ok(userService.getUserByUserId(userId));
    }

    @DeleteMapping("users/{id}")
    public ResponseEntity<Long> delete(
            @PathVariable("id") Long userId
    ) {
        return ResponseEntity.ok(userService.deleteUser(userId));
    }
}
