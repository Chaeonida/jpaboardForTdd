package com.tdd.jpaboardfortdd.service;

import com.tdd.jpaboardfortdd.domain.Post;
import com.tdd.jpaboardfortdd.domain.PostLikes;
import com.tdd.jpaboardfortdd.domain.User;
import com.tdd.jpaboardfortdd.dto.PostLikesResponse;
import com.tdd.jpaboardfortdd.repository.PostLikesRepository;
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
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class PostLikesTest {
    @Mock
    private PostRepository postRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PostLikesRepository postLikesRepository;

    @InjectMocks
    private PostLikesService postLikesService;

    static User user = User.builder().id(1L).age(14).name("ChaeWon").hobby("drawing").build();
    static Post post = Post.builder().id(1L).content("아무내용").title("제목").user(user).build();
    static PostLikes postLike = PostLikes.builder().id(1L).post(post).user(user).build();

    @Test
    @DisplayName("게시판좋아요 등록 테스트 ")
    void createPostLikeTest() {
        //given(user,Post 가 주어졌을때 )
        Mockito.when(userRepository.findById(any())).thenReturn(Optional.of(user));
        Mockito.when(postRepository.findById(any())).thenReturn(Optional.of(post));
        Mockito.when(postLikesRepository.save(any())).thenReturn(postLike);

        //when(user가 게시판에 좋아요를 누르면)
        PostLikes savedPostLikes = postLikesService.save(user.getId(), post.getId());

        //then(등록이 되어야한다.)
        assertThat(savedPostLikes.getId(), is(1L));
        assertThat(savedPostLikes.getUser().getId(), is(1L));
        assertThat(savedPostLikes.getPost().getId(), is(1L));
    }

    @Test
    @DisplayName("게시판좋아요 조회 테스트 ")
    void getPostLikeTest() {
        //given(Post와 Post에 해당하는 댓글이 주어졌을때 )
        List<PostLikes> postLikes = new ArrayList<>();
        postLikes.add(postLike);

        Mockito.when(postRepository.findById(any())).thenReturn(Optional.of(post));
        Mockito.when(postLikesRepository.getByPost(any())).thenReturn(postLikes);

        //when(좋아요를 조회하면)
        List<PostLikesResponse> postLikesResponses = postLikesService.get(post.getId());

        //then(조회가 되어야한다.)
        assertThat(postLikesResponses.size(), is(1));
    }

    @Test
    @DisplayName("게시판좋아요 삭제 테스트 ")
    void deletePostLikesTest() {
        //given(Post 와 PostLike가 주어졌을때 )
        Mockito.when(userRepository.findById(any())).thenReturn(Optional.of(user));
        Mockito.when(postRepository.findById(any())).thenReturn(Optional.of(post));
        Mockito.when(postLikesRepository.deleteByUserIdAndPostId(any(), any())).thenReturn(1L);

        //when(user가 좋아요를 삭제하면)
        Long deletedId = postLikesService.delete(user.getId(), post.getId());

        //then(좋아요가 삭제 되어야한다.)
        assertThat(deletedId, is(1L));
    }
}
