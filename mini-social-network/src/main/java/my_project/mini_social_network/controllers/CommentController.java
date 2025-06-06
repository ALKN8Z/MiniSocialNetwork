package my_project.mini_social_network.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import my_project.mini_social_network.annotations.IsOwnerOrAdmin;
import my_project.mini_social_network.dto.CommentDto;
import my_project.mini_social_network.dto.responses.CommentResponse;
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
    public ResponseEntity<List<CommentResponse>> getCommentsByUser(@PathVariable int user_id) {
        User user = new User();
        user.setId(user_id);

        List<Comment> comments = commentService.findByUser(user);
        return new ResponseEntity<>(comments.stream().map(comment ->
                modelMapper.map(comment, CommentResponse.class)).toList(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Comment> createComment(@RequestBody @Valid CommentDto commentDto) {
        Comment newComment = commentService.saveComment(commentDto);
        return new ResponseEntity<>(newComment, HttpStatus.CREATED);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentResponse>> getCommentsByPost(@PathVariable int postId) {
        Post post = new Post();
        post.setId(postId);

        List<Comment> comments = commentService.findByPost(post);
        return new ResponseEntity<>(comments.stream().map(comment ->
                modelMapper.map(comment, CommentResponse.class)).toList(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @IsOwnerOrAdmin(indexOfParameterContainsResourceId = 0, classOfRequiredMethod = CommentService.class, methodName = "findByCommentId")
    public ResponseEntity<CommentResponse> updateComment(@PathVariable int id, @RequestBody @Valid CommentDto commentDto) {
        return new ResponseEntity<>(modelMapper.map(commentService.updateComment(id, commentDto), CommentResponse.class), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @IsOwnerOrAdmin(indexOfParameterContainsResourceId = 0, classOfRequiredMethod = CommentService.class, methodName = "findByCommentId")
    public ResponseEntity<Void> deleteComment(@PathVariable int id) {
        commentService.deleteComment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
