package com.tdd.jpaboardfortdd.service;

import com.tdd.jpaboardfortdd.domain.Post;
import com.tdd.jpaboardfortdd.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
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

        //when(user가 게시글을 작성하면)
        Post savedPost = postService.save(post);

        //then(등록이 되어야한다.)
        assertThat(savedPost.getContent(), is("아무내용"));
    }
}

