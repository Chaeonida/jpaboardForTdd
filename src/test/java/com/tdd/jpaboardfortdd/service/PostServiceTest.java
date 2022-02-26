package com.tdd.jpaboardfortdd.service;

import com.tdd.jpaboardfortdd.domain.Post;
import com.tdd.jpaboardfortdd.domain.User;
import com.tdd.jpaboardfortdd.dto.PostCreateRequest;
import com.tdd.jpaboardfortdd.dto.PostUpdateRequest;
import com.tdd.jpaboardfortdd.repository.PostRepository;
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
public class PostServiceTest {

    @Mock
    private PostRepository postRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PostService postService;

    static User user = User.builder().id(1L).age(14).name("ChaeWon").hobby("drawing").build();
    static Post post = Post.builder().id(1L).content("아무내용").title("제목").user(user).build();

    @Test
    @DisplayName("게시글 등록 테스트 ")
    void createPostTest() {
        //given(user,Post 가 주어졌을때 )
        Mockito.when(userRepository.findById(any())).thenReturn(Optional.of(user));
        Mockito.when(postRepository.save(any())).thenReturn(post);

        PostCreateRequest postCreateRequest = PostCreateRequest.builder()
                .title("제목")
                .content("아무내용")
                .build();

        //when(user가 게시글을 작성하면)
        Post savedPost = postService.savePost(postCreateRequest, user.getId());

        //then(등록이 되어야한다.)
        assertThat(savedPost.getId(), is(1L));
        assertThat(savedPost.getUser().getId(), is(1L));
        assertThat(savedPost.getContent(), is("아무내용"));
        assertThat(savedPost.getTitle(), is("제목"));
    }

    @Test
    @DisplayName("게시글 수정 테스트 ")
    void updatePostTest() {
        //given(저장 되어 있는 Post 가 주어졌을때 )
        Mockito.when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));
        PostUpdateRequest postUpdateRequest = PostUpdateRequest.builder()
                .content("내용수정")
                .title("제목수정")
                .build();

        //when(게시글을 수정 하면)
        Post updatedPost = postService.updatePost(postUpdateRequest, post.getId(), user.getId());

        //then(게시글이 수정 되어야 한다.)
        assertThat(updatedPost.getContent(), is("내용수정"));
        assertThat(updatedPost.getTitle(), is("제목수정"));
    }

    @Test
    @DisplayName("게시글 수정 실패 테스트 ")
    void updatePostFailTest() {
        //given(저장 되어 있는 Post 가 주어졌을때 )
        Mockito.when(postRepository.findById(post.getId())).thenReturn(Optional.empty());
        PostUpdateRequest postUpdateRequest = PostUpdateRequest.builder()
                .content("내용수정")
                .title("제목수정")
                .build();

        //when(게시글을 수정 하면)
        //then(작성한 user와 다를경우 예외가 나타난다)
        assertThrows(IllegalArgumentException.class, () -> postService.updatePost(
                postUpdateRequest, 1L, 1L));
    }

    @Test
    @DisplayName("게시글 조회 테스트 ")
    void findPostTest() {
        //given(저장 되어 있는 Post 가 주어졌을때 )
        Mockito.when(postRepository.findById(any())).thenReturn(Optional.of(post));

        //when(게시글을 조회 하면)
        Post findPost = postService.getPostById(post.getId());

        //then(게시글이 조회 되어야 한다.)
        assertThat(findPost.getId(), is(post.getId()));
    }

    @Test
    @DisplayName("게시글 조회 실패 테스트 ")
    void findPostFailTest() {
        //given(저장 되어 있는 Post 가 주어졌을때 )
        Mockito.when(postRepository.findById(any())).thenReturn(Optional.empty());

        //when(맞지않는 아이디로 조회시)
        //then(실해패야한다.)
        assertThrows(IllegalArgumentException.class, () -> postService.getPostById(2L));
    }

    @Test
    @DisplayName("게시글 삭제 테스트 ")
    void deletePostTest() {
        //given(저장 되어 있는 Post 가 주어졌을때 )
        doNothing().when(postRepository).delete(any());

        //when(게시글을 삭제 하면)
        Long deletedPostId = postService.deletePost(post.getId(), user.getId());

        //then(게시글이 삭제 되어야 한다.)
        assertThat(deletedPostId, is(post.getId()));
    }
}

