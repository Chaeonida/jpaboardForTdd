package com.tdd.jpaboardfortdd.controller;

import com.tdd.jpaboardfortdd.dto.UserCreateRequest;
import com.tdd.jpaboardfortdd.dto.UserDetailResponse;
import com.tdd.jpaboardfortdd.dto.UserUpdateRequest;
import com.tdd.jpaboardfortdd.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "users")
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<Long> createUser(
            @RequestBody UserCreateRequest userCreateRequest
    ) {
        return ResponseEntity.ok(userService.save(userCreateRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateUser(
            @PathVariable("id") Long userId, @RequestBody UserUpdateRequest userUpdateRequest
    ) {
        return ResponseEntity.ok(userService.update(userUpdateRequest, userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDetailResponse> get(
            @PathVariable("id") Long userId
    ) {
        return ResponseEntity.ok(userService.get(userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(
            @PathVariable("id") Long userId
    ) {
        return ResponseEntity.ok(userService.delete(userId));
    }
}
