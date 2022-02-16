package com.tdd.jpaboardfortdd.service;

import com.tdd.jpaboardfortdd.domain.Post;
import com.tdd.jpaboardfortdd.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

import static org.springframework.data.crossstore.ChangeSetPersister.*;

@Transactional
@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public Post save(Post post) {
        return postRepository.save(post);
    }

    public Post update(Long id, String title, String content) throws NotFoundException {
        Post findPost = postRepository.findById(id).orElseThrow(NotFoundException::new);

        findPost.updatePost(title, content);
        return findPost;


    }

    public Post find(Long id) throws NotFoundException {
        return postRepository.findById(id).orElseThrow(NotFoundException::new);
    }
}
