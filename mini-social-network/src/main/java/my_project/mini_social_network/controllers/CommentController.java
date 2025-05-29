package my_project.mini_social_network.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import my_project.mini_social_network.dto.CommentDto;
import my_project.mini_social_network.models.Comment;
import my_project.mini_social_network.models.Post;
import my_project.mini_social_network.models.User;
import my_project.mini_social_network.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final ModelMapper modelMapper;

    @GetMapping("/user/{user_id}")
    public ResponseEntity<List<Comment>> getCommentByUser(@PathVariable int user_id) {
        User user = new User();
        user.setId(user_id);

        List<Comment> comments = commentService.findByUser(user);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Comment> createComment(@RequestBody @Valid CommentDto commentDto) {
        Comment newComment = commentService.saveComment(modelMapper.map(commentDto, Comment.class));
        return new ResponseEntity<>(newComment, HttpStatus.CREATED);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<Comment>> getCommentsByPost(@PathVariable int postId) {
        Post post = new Post();
        post.setId(postId);

        List<Comment> comments = commentService.findByPost(post);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable int id, @RequestBody @Valid CommentDto commentDto) {
        return new ResponseEntity<>(commentService.updateComment(id, commentDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable int id) {
        commentService.deleteComment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
