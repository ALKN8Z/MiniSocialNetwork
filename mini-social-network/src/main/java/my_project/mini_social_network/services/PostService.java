package my_project.mini_social_network.services;

import my_project.mini_social_network.dto.PostDto;
import my_project.mini_social_network.models.Post;
import my_project.mini_social_network.models.User;

import java.util.List;
import java.util.Optional;

public interface PostService {
    Post savePost(PostDto postDto);
    Post updatePost(PostDto postDto, int postId);
    void deletePost(int postId);
    List<Post> findByUser(User user);
    List<Post> searchPostByTitle(String title);
    Post findByPostId(int postId);
}
