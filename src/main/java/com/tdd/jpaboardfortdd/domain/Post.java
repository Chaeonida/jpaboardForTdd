package com.tdd.jpaboardfortdd.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "post")
@Getter
@NoArgsConstructor
@Setter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    @Lob
    private String content;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<PostLikes> postLikes = new ArrayList<>();

    @Builder
    public Post(Long id, String title, String content, User user) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.user = user;
    }

    @Builder
    public Post(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
    }

    public void setUser(User user) {
        if (Objects.nonNull(this.user)) {
            this.user.getPosts().remove(this);
        }

        this.user = user;
        user.getPosts().add(this);
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
        comment.setRoutinePost(this);
    }

    public Post addComment(List<Comment> comments) {
        comments.forEach(this::addComment);
        return this;
    }

    public void addPostLike(PostLikes postLike) {
        this.postLikes.add(postLike);
        postLike.setPost(this);
    }

    public Post addPostLikes(List<PostLikes> postLikes) {
        postLikes.forEach(this::addPostLike);
        return this;
    }

    public void updatePost(String title, String content){
        this.content = content;
        this.title = title;
    }

}