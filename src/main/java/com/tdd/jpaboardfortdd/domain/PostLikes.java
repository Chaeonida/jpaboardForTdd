package com.tdd.jpaboardfortdd.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Entity
@Table(name = "post_likes")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostLikes {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Builder
    public PostLikes(Long id, User user, Post post) {
        this.id = id;
        this.user = user;
        this.post = post;
    }

    public void setPost(Post post) {
        if (Objects.nonNull(this.post)) {
            this.post.getPostLikes()
                    .remove(this);
        }
        this.post = post;
    }
}
