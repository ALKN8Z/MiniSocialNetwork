package my_project.mini_social_network.services.impl;

import lombok.RequiredArgsConstructor;
import my_project.mini_social_network.dto.PostDto;
import my_project.mini_social_network.exceptions.ResourceNotFoundException;
import my_project.mini_social_network.models.Post;
import my_project.mini_social_network.models.User;
import my_project.mini_social_network.repositories.PostRepository;
import my_project.mini_social_network.repositories.UserRepository;
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
    private final UserRepository userRepository;

    @Override
    @Transactional
    public Post savePost(PostDto postDto) {
        User user = userRepository.findById(postDto.getUserId()).orElseThrow(()
                -> new ResourceNotFoundException("User with this id not found"));

        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setUser(user);

        return postRepository.save(post);
    }

    @Override
    @Transactional
    public Post updatePost(PostDto postDto, int postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post with this id not found!"));

        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());

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

    @Override
    public Post findByPostId(int postId) {
        return postRepository.findById(postId).orElse(null);
    }
}
