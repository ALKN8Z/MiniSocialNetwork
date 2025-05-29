package my_project.mini_social_network.services;

import my_project.mini_social_network.dto.CommentDto;
import my_project.mini_social_network.dto.CommentRequest;
import my_project.mini_social_network.models.Comment;
import my_project.mini_social_network.models.Post;
import my_project.mini_social_network.models.User;

import java.util.List;

public interface CommentService {
    Comment saveComment(Comment comment);
    List<Comment> findByPost(Post post);
    List<Comment> findByUser(User user);
    Comment updateComment(int commentId, CommentDto commentDto);
    void deleteComment(int commentId);
}
