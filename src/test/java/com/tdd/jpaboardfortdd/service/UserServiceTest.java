package com.tdd.jpaboardfortdd.service;

import com.tdd.jpaboardfortdd.domain.Post;
import com.tdd.jpaboardfortdd.domain.User;
import com.tdd.jpaboardfortdd.dto.UserCreateRequest;
import com.tdd.jpaboardfortdd.repository.PostRepository;
import com.tdd.jpaboardfortdd.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    static User user = User.builder().id(1L).age(14).name("ChaeWon").hobby("drawing").build();
    static Post post = Post.builder().id(1L).content("아무내용").title("제목").user(user).build();

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
        Long updatedUserId = userService.update(userUpdateRequest);

        //then(등록이 되어야한다.)
        assertThat(updatedUserId, is(1L));
    }

    @Test
    @DisplayName("유저 조회 테스트 ")
    void getUserTest() {
        //given(저장 되어 있는 Post 가 주어졌을때 )
        Mockito.when(userRepository.findById(any())).thenReturn(java.util.Optional.of(user));

        //when(게시글을 조회 하면)
        UserDetaioDto userDetaioDto = userService.get(user.getId());

        //then(게시글이 조회 되어야 한다.)
        assertThat(userDetaioDto.getId(), is(user.getId()));
    }

    @Test
    @DisplayName("유저 조회 실패 테스트 ")
    void findPostFailTest() {
        //given(저장 되어 있는 Post 가 주어졌을때 )
        Mockito.when(userRepository.findById(any())).thenReturn(java.util.Optional.of(null));

        //when(게시글을 조회 하면)
        UserDetaioDto userDetaioDto = userService.get(user.getId());

        //then(게시글이 조회 되어야 한다.)
        assertThat(userDetaioDto.getId(), is(null));
    }

    @Test
    @DisplayName("유저 삭제 테스트 ")
    void deletePostTest() {
        //given(저장 되어 있는 Post 가 주어졌을때 )

        //when(게시글을 삭제 하면)
        Long deletedUserId = userService.delete(post.getId());

        //then(게시글이 삭제 되어야 한다.)
        assertThat(deletedUserId, is(user.getId()));
    }
}
