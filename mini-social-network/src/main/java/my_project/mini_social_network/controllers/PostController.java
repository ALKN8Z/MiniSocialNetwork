package my_project.mini_social_network.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import my_project.mini_social_network.dto.PostDto;
import my_project.mini_social_network.models.Post;
import my_project.mini_social_network.models.User;
import my_project.mini_social_network.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final ModelMapper modelMapper;

    @GetMapping("/search")
    public ResponseEntity<List<PostDto>> searchPost(@RequestParam String title) {
        List<Post> posts = postService.searchPostByTitle(title);
        return new ResponseEntity<>(posts.stream().map(
                post -> modelMapper.map(post, PostDto.class)).toList(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto) {
        Post savedPost = postService.savePost(modelMapper.map(postDto, Post.class));
        return new ResponseEntity<>(modelMapper.map(savedPost, PostDto.class), HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PostDto>> getPostByUserId(@PathVariable int userId) {
        User user = new User();
        user.setId(userId);
        List<Post> posts = postService.findByUser(user);
        return new ResponseEntity<>(posts.stream().map(
                post -> modelMapper.map(post, PostDto.class)).toList(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable int id, @Valid @RequestBody PostDto postDto) {
        return new ResponseEntity<>(modelMapper.map(
                postService.savePost(modelMapper.map(postDto, Post.class)), PostDto.class), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable int id) {
        postService.deletePost(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
