package my_project.mini_social_network.services.impl;

import lombok.RequiredArgsConstructor;
import my_project.mini_social_network.models.Post;
import my_project.mini_social_network.models.User;
import my_project.mini_social_network.repositories.PostRepository;
import my_project.mini_social_network.services.PostService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;

    @Override
    @Transactional
    public Post savePost(Post post) {
        return postRepository.save(post);
    }

    @Override
    @Transactional
    public void deletePost(int postId) {
        postRepository.deleteById(postId);
    }

    @Override
    public List<Post> findByUser(User user) {
        return postRepository.findByUser(user);
    }

    @Override
    public List<Post> searchPostByTitle(String title) {
        return postRepository.findPostsByTitleContaining(title);
    }
}
