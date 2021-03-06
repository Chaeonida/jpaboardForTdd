package com.tdd.jpaboardfortdd.service;

import com.tdd.jpaboardfortdd.domain.Comment;
import com.tdd.jpaboardfortdd.domain.Post;
import com.tdd.jpaboardfortdd.domain.User;
import com.tdd.jpaboardfortdd.dto.CommentCreateRequest;
import com.tdd.jpaboardfortdd.dto.CommentListResponse;
import com.tdd.jpaboardfortdd.dto.CommentUpdateRequest;
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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

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
    static Comment comment = Comment.builder().id(1L).content("댓글내용1").post(post).user(user).build();

    @Test
    @DisplayName("댓글 등록 테스트 ")
    void createCommentTest(Long userId) {
        //given(user,Post 가 주어졌을때 )
        Mockito.when(userRepository.findById(any())).thenReturn(Optional.of(user));
        Mockito.when(postRepository.findById(any())).thenReturn(Optional.of(post));
        Mockito.when(commentRepository.save(any())).thenReturn(comment);

        CommentCreateRequest commentRequest = CommentCreateRequest.builder()
                .content("댓글내용1")
                .build();

        //when(user가 댓글을 작성하면)
        Comment savedComment = commentService.saveComment(commentRequest, post.getId(), user.getId());

        //then(등록이 되어야한다.)
        assertThat(savedComment.getId(), is(1L));
        assertThat(savedComment.getUser().getId(), is(1L));
        assertThat(savedComment.getPost().getId(), is(1L));
        assertThat(savedComment.getContent(), is("댓글내용1"));
    }

    @Test
    @DisplayName("댓글 조회 테스트 ")
    void getCommentsTest() {
        //given(Post와 Post에 해당하는 댓글이 주어졌을때 )
        Comment comment2 = Comment.builder().post(post).user(user).content("댓글내용2").build();
        List<Comment> comments = Arrays.asList(comment, comment2);

        Mockito.when(postRepository.findById(any())).thenReturn(Optional.of(post));
        Mockito.when(commentRepository.findByPost(any())).thenReturn(comments);

        //when(user가 댓글을 조회하면)
        List<CommentListResponse> commentListResponses = commentService.getCommentByPostId(post.getId());

        //then(조회가 되어야한다.)
        assertThat(commentListResponses.size(), is(2));
        for (int i = 0; i < commentListResponses.size(); i++) {
            String s = "댓글내용";
            s += String.valueOf(i + 1);
            assertThat(commentListResponses.get(i).getContent(), is(s));
        }
    }

    @Test
    @DisplayName("댓글 수정 테스트 ")
    void updateCommentTest() {
        //given(Post와 Post에 해당하는 댓글이 주어졌을때 )
        Mockito.when(commentRepository.findById(any())).thenReturn(Optional.of(comment));
        CommentUpdateRequest commentUpdateRequest = CommentUpdateRequest.builder()
                .content("댓글수정")
                .build();

        //when(user가 댓글을 수정하면)
        Comment updatedComment = commentService.updateComment(commentUpdateRequest, comment.getId(), user.getId());

        //then(수정 되어야한다.)
        assertThat(updatedComment.getId(), is(1L));
        assertThat(updatedComment.getContent(), is("댓글수정"));
    }

    @Test
    @DisplayName("댓글 수정 실패 테스트 ")
    void updateCommentFailTest() {
        //given(Post와 Post에 해당하는 댓글이 주어졌을때 )
        Mockito.when(commentRepository.findById(any())).thenReturn(Optional.empty());
        CommentUpdateRequest commentUpdateRequest = CommentUpdateRequest.builder()
                .content("댓글수정")
                .build();

        //when(user가 댓글을 수정하면)
        //then(댓글 작성자와 다를경우 예외가 나타난다.)
        assertThrows(IllegalArgumentException.class, () -> commentService.updateComment(
                commentUpdateRequest,
                1L,
                1L));
    }

    @Test
    @DisplayName("댓글 삭제 테스트 ")
    void deleteCommentTest() {
        //given(Post와 Post에 해당하는 댓글이 주어졌을때 )
        Mockito.when(commentRepository.findById(any())).thenReturn(Optional.of(comment));
        doNothing().when(commentRepository).delete(any());

        //when(user가 댓글을 삭제하면)
        Long deletedId = commentService.deleteComment(user.getId(), comment.getId());

        //then(댓글이 삭제 되어야한다.)
        assertThat(deletedId, is(1L));
    }
}
