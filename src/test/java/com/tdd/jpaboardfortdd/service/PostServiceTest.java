package com.tdd.jpaboardfortdd.service;

import com.tdd.jpaboardfortdd.domain.Post;
import com.tdd.jpaboardfortdd.domain.User;
import com.tdd.jpaboardfortdd.repository.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostService postService;

    static User user = User.builder().id(1L).age(14).name("ChaeWon").hobby("drawing").build();
    static Post post = Post.builder().id(1L).content("아무내용").title("제목").user(user).build();

    @Test
    @DisplayName("게시글 등록 테스트 ")
    void createPostTest() {
        //given(user,Post 가 주어졌을때 )
        Mockito.when(postRepository.save(post)).thenReturn(post);

        //when(user가 게시글을 작성하면)
        Post savedPost = postService.save(post);

        //then(등록이 되어야한다.)
        assertThat(savedPost.getId(), is(1L));
    }

    @Test
    @DisplayName("게시글 수정 테스트 ")
    void updatePostTest() throws ChangeSetPersister.NotFoundException {
        //given(저장 되어 있는 Post 가 주어졌을때 )
        Mockito.when(postRepository.findById(post.getId())).thenReturn(java.util.Optional.of(post));

        //when(게시글을 수정 하면)
        Post updatedPost = postService.update(post.getId(), "제목수정", "내용수정");

        //then(게시글이 수정 되어야 한다.)
        assertThat(updatedPost.getContent(), is("내용수정"));
        assertThat(updatedPost.getTitle(), is("제목수정"));

    }

    @Test
    @DisplayName("게시글 조회 테스트 ")
    void findPostTest() {
        //given(저장 되어 있는 Post 가 주어졌을때 )
        Mockito.when(postRepository.findById(post.getId())).thenReturn(java.util.Optional.of(post));

        //when(게시글을 조회 하면)
        Post findPost = postService.find(post.getId());

        //then(게시글이 조회 되어야 한다.)
        assertThat(findPost.getId(), is(post.getId()));
        assertThrows(IllegalArgumentException.class,()-> postService.find(2L));

    }

    @Test
    @DisplayName("게시글 조회 실패 테스트 ")
    void findPostFailTest() {
        //given(저장 되어 있는 Post 가 주어졌을때 )

        //when(게시글을 조회 하면)
        Mockito.when(postRepository.findById(2L)).thenThrow(new IllegalArgumentException());

        //then(게시글이 조회 되어야 한다.)
        assertThrows(IllegalArgumentException.class,()-> postService.find(2L));

    }

    @Test
    @DisplayName("게시글 삭제 테스트 ")
    void deletePostTest() {
        //given(저장 되어 있는 Post 가 주어졌을때 )

        //when(게시글을 삭제 하면)
        Long deletedPostId = postService.delete(post.getId());

        //then(게시글이 삭제 되어야 한다.)
        assertThat(deletedPostId, is(post.getId()));

    }

}

