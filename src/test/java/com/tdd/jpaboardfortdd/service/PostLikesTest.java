package com.tdd.jpaboardfortdd.service;

import com.tdd.jpaboardfortdd.domain.Comment;
import com.tdd.jpaboardfortdd.domain.Post;
import com.tdd.jpaboardfortdd.domain.PostLikes;
import com.tdd.jpaboardfortdd.domain.User;
import com.tdd.jpaboardfortdd.dto.*;
import com.tdd.jpaboardfortdd.repository.CommentRepository;
import com.tdd.jpaboardfortdd.repository.PostRepository;
import com.tdd.jpaboardfortdd.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
public class PostLikesTest {
    @Mock
    private PostRepository postRepository;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PostLikesRepository postLikesRepository;

    @InjectMocks
    private PostLikesService postLikesService;

    static User user = User.builder().id(1L).age(14).name("ChaeWon").hobby("drawing").build();
    static Post post = Post.builder().id(1L).content("아무내용").title("제목").user(user).build();
    static Comment comment = Comment.builder().id(1L).content("댓글내용").post(post).user(user).build();
    static PostLikes postLikes = PostLikes.builder().id(1L).post(post).user(user).build();


    @Test
    @DisplayName("게시판좋아요 등록 테스트 ")
    void createCommentTest() {
        //given(user,Post 가 주어졌을때 )
        Mockito.when(userRepository.findById(any())).thenReturn(Optional.of(user));
        Mockito.when(postRepository.findById(any())).thenReturn(Optional.of(post));
        Mockito.when(postLikesRepository.save(any())).thenReturn(postLikes);

        PostCreateRequest postCreateRequest = PostCreateRequest.userId(1L).builder().build();

        //when(user가 게시판에 좋아요를 누르면)
       PostLikes savedPostLikes = postLikesService.save(postCreateRequest, post.getId());

        //then(등록이 되어야한다.)
        assertThat(savedPostLikes.getId(), is(1L));
        assertThat(savedPostLikes.getUser().getId(), is(1L));
        assertThat(savedPostLikes.getPost().getId(), is(1L));
    }
}
