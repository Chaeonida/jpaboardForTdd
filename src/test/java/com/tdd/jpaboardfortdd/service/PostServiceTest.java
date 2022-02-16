package com.tdd.jpaboardfortdd.service;

import com.tdd.jpaboardfortdd.domain.Post;
import com.tdd.jpaboardfortdd.domain.User;
import com.tdd.jpaboardfortdd.repository.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.crossstore.ChangeSetPersister;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

public class PostServiceTest {

    @Test
    @DisplayName("게시글 등록 테스트 ")
    void createPostTest() {
        //given(user,Post 가 주어졌을때 )
        User user = User.builder().age(14).name("ChaeWon").hobby("drawing").build();
        Post post = Post.builder().content("아무내용").title("제목").user(user).build();
        PostRepository postRepository = Mockito.mock(PostRepository.class);
        Mockito.when(postRepository.save(post)).thenReturn(post);
        PostService postService = new PostService(postRepository);

        //when(user가 게시글을 작성하면)
        Post savedPost = postService.save(post);

        //then(등록이 되어야한다.)
        assertThat(savedPost.getContent(), is("아무내용"));
    }

    @Test
    @DisplayName("게시글 수정 테스트 ")
    void updatePostTest() throws ChangeSetPersister.NotFoundException {
        //given(저장 되어 있는 Post 가 주어졌을때 )
        User user = User.builder().age(14).name("ChaeWon").hobby("drawing").build();
        Post post = Post.builder().content("아무내용").title("제목").user(user).build();
        PostRepository postRepository = Mockito.mock(PostRepository.class);
        Mockito.when(postRepository.findById(post.getId())).thenReturn(java.util.Optional.of(post));
        PostService postService = new PostService(postRepository);

        //when(게시글을 수정 하면)
        Post updatedPost = postService.update(post.getId(), "제목수정", "내용수정");

        //then(게시글이 수정 되어야 한다.)
        assertThat(updatedPost.getContent(), is("내용수정"));
        assertThat(updatedPost.getTitle(), is("제목수정"));

    }

}

