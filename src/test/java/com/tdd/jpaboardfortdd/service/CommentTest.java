package com.tdd.jpaboardfortdd.service;

import com.tdd.jpaboardfortdd.domain.Comment;
import com.tdd.jpaboardfortdd.domain.Post;
import com.tdd.jpaboardfortdd.domain.User;
import com.tdd.jpaboardfortdd.dto.CommentCreateRequest;
import com.tdd.jpaboardfortdd.dto.CommentListResponse;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    void createCommentTest() {
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

    @Test
    @DisplayName("댓글 조회 테스트 ")
    void getCommentsTest() {
        //given(Post와 Post에 해당하는 댓글이 주어졌을때 )
        List<Comment> comments = new ArrayList<>();
        Comment comment2 = Comment.builder().post(post).user(user).content("댓글내용2").build();
        comments.add(comment);
        comments.add(comment2);

        Mockito.when(postRepository.findById(any())).thenReturn(Optional.of(post));
        Mockito.when(commentRepository.findByPost(any())).thenReturn(comments);

        //when(user가 댓글을 조회하면)
        List<CommentListResponse> commentListResponseList = commentService.get(post.getId());

        //then(조회가 되어야한다.)
        assertThat(commentListResponseList.size(), is(2));
        assertThat(commentListResponseList.get(0).getContent(), is("댓글내용"));
        assertThat(commentListResponseList.get(1).getContent(), is("댓글내용2"));
    }

    @Test
    @DisplayName("댓글 수정 테스트 ")
    void updateCommentTest() {
        //given(Post와 Post에 해당하는 댓글이 주어졌을때 )
        Mockito.when(userRepository.findById(any())).thenReturn(Optional.of(user));
        Mockito.when(postRepository.findById(any())).thenReturn(Optional.of(post));
        Mockito.when(commentRepository.findById(any())).thenReturn(Optional.of(comment));

        //when(user가 댓글을 수정하면)
        CommentUpdateRequest commentUpdateRequest = CommentUpdateRequest.builder()
                .content("댓글수정")
                .userId(1L)
                .build();

       Comment updatedComment = commentService.update(commentUpdateRequest);

        //then(수정 되어야한다.)
        assertThat(updatedComment.getId(), is(1L));
        assertThat(updatedComment.getContent(), is("댓글수정"));
    }

    @Test
    @DisplayName("댓글 실패 테스트 ")
    void updateCommentFailTest() {
        //given(Post와 Post에 해당하는 댓글이 주어졌을때 )
        Mockito.when(userRepository.findById(any())).thenReturn(Optional.of(user));
        Mockito.when(postRepository.findById(any())).thenReturn(Optional.of(post));
        Mockito.when(commentRepository.findById(any())).thenReturn(Optional.of(comment));

        //when(user가 댓글을 수정하면)
        CommentUpdateRequest commentUpdateRequest = CommentUpdateRequest.builder()
                .content("댓글수정")
                .userId(1L)
                .build();

        //then(댓글 작성자와 다를경우 예외가 나타난다.)
        assertThrows(IllegalArgumentException.class, () -> commentService.update(commentUpdateRequest);
    }
}
