package com.tdd.jpaboardfortdd.domain;

import com.tdd.jpaboardfortdd.dto.CommentListResponse;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "comment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Builder
    public Comment(Long id, String content, User user, Post post) {
        this.id = id;
        this.content = content;
        this.user = user;
        this.post = post;
    }

    public void setRoutinePost(Post post) {
        if (Objects.nonNull(this.post)) {
            this.post.getComments()
                    .remove(this);
        }
        this.post = post;
    }

    public CommentListResponse toCommentListResponse() {
        return CommentListResponse.builder()
                .userId(user.getId())
                .content(content)
                .build();
    }

    public void update(String content) {
        this.content = content;
    }

    public Long getUserId() {
        return user.getId();
    }
}
