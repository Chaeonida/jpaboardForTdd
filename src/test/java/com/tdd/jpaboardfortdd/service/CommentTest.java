package com.tdd.jpaboardfortdd.service;

import com.tdd.jpaboardfortdd.domain.Comment;
import com.tdd.jpaboardfortdd.domain.Post;
import com.tdd.jpaboardfortdd.domain.User;
import com.tdd.jpaboardfortdd.dto.CommentCreateRequest;
import com.tdd.jpaboardfortdd.dto.PostCreateRequest;
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

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class CommentTest {
    @Mock
    private PostRepository postRepository;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CommentService commentService;

    static User user = User.builder().id(1L).age(14).name("ChaeWon").hobby("drawing").build();
    static Post post = Post.builder().id(1L).content("아무내용").title("제목").user(user).build();
    static Comment comment = Comment.builder().id(1L).content("댓글내용").post(post).user(user).build();

    @Test
    @DisplayName("댓글 등록 테스트 ")
    void createPostTest() {
        //given(user,Post 가 주어졌을때 )
        Mockito.when(userRepository.findById(any())).thenReturn(Optional.of(user));
        Mockito.when(postRepository.findById(any())).thenReturn(Optional.of(post));
        Mockito.when(commentRepository.save(any())).thenReturn(comment);

        CommentCreateRequest commentRequest = CommentCreateRequest.builder()
                .content("댓글내용")
                .userId(1L)
                .build();

        //when(user가 댓글을 작성하면)
        Comment savedComment = commentService.save(commentRequest,post.getId());

        //then(등록이 되어야한다.)
        assertThat(savedComment.getId(), is(1L));
        assertThat(savedComment.getUser().getId(), is(1L));
        assertThat(savedComment.getPost().getId(), is(1L));
    }


}
