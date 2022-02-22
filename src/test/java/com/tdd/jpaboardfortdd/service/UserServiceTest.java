package com.tdd.jpaboardfortdd.service;

import com.tdd.jpaboardfortdd.domain.User;
import com.tdd.jpaboardfortdd.dto.UserCreateRequest;
import com.tdd.jpaboardfortdd.dto.UserDetailResponse;
import com.tdd.jpaboardfortdd.dto.UserUpdateRequest;
import com.tdd.jpaboardfortdd.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    static User user = User.builder().id(1L).age(14).name("ChaeWon").hobby("drawing").build();

    @Test
    @DisplayName("유저 등록 테스트 ")
    void createUserTest() {
        //given(userRequest가 주어졌을때)
        UserCreateRequest userCreateRequest = UserCreateRequest.builder().age(14).name("ChaeWon").hobby("drawing").build();
        Mockito.when(userRepository.save(any())).thenReturn(user);

        //when(user가 회원가입을 하면)
        Long savedUserId = userService.save(userCreateRequest);

        //then(등록이 되어야한다.)
        assertThat(savedUserId, is(1L));
    }

    @Test
    @DisplayName("유저 수정 테스트 ")
    void updateUserTest() {
        //given(userRequest가 주어졌을때)
        UserUpdateRequest userUpdateRequest = UserUpdateRequest.builder().age(14).name("ChaeWonida").hobby("Movie").build();
        Mockito.when(userRepository.findById(any())).thenReturn(java.util.Optional.of(user));

        //when(user가 회원가입을 하면)
        Long updatedUserId = userService.update(userUpdateRequest, user.getId());

        //then(등록이 되어야한다.)
        assertThat(updatedUserId, is(1L));
    }

    @Test
    @DisplayName("유저 조회 테스트 ")
    void getUserTest() {
        //given(저장 되어 있는 user 가 주어졌을때 )
        Mockito.when(userRepository.findById(any())).thenReturn(java.util.Optional.of(user));

        //when(유저를 조회 하면)
        UserDetailResponse userDetailResponse = userService.get(user.getId());

        //then(유저가 조회 되어야 한다.)
        assertThat(userDetailResponse.getId(), is(user.getId()));
    }

    @Test
    @DisplayName("유저 조회 실패 테스트 ")
    void findUserFailTest() {
        //given(저장 되어 있는 User 가 주어졌을때 )
        Mockito.when(userRepository.findById(any())).thenReturn(Optional.empty());

        //when(저장되어있지 않은 id로 유저를 조회 하면)

        //then(예외가 터져야 한다.)
        assertThrows(IllegalArgumentException.class, () -> userService.get(2L));
    }

    @Test
    @DisplayName("유저 삭제 테스트 ")
    void deleteUserTest() {
        //given(저장 되어 있는 User 가 주어졌을때 )
        Mockito.when(userRepository.findById(any())).thenReturn(java.util.Optional.of(user));
        doNothing().when(userRepository).delete(any());

        //when(유저를 삭제 하면)
        Long deletedUserId = userService.delete(user.getId());

        //then(유저는 삭제 되어야 한다.)
        assertThat(deletedUserId, is(user.getId()));
    }
}
