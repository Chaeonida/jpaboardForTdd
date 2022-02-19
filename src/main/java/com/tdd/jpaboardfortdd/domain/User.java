package com.tdd.jpaboardfortdd.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "name", nullable = false, length = 30)
	private String name;
	
	private String hobby;
	
	private int age;
	
	@OneToMany(mappedBy = "user")
	private List<Post> posts = new ArrayList<>();


	@Builder
    public User(Long id, String name, String hobby, int age) {
        this.id = id;
        this.name = name;
        this.hobby = hobby;
        this.age = age;
    }

    public void update(String name, String hobby, int age) {
	    this.name = name;
	    this.age = age;
        this.hobby = hobby;
    }
}