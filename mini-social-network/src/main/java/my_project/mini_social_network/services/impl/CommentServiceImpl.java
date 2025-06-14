package my_project.mini_social_network.services.impl;

import lombok.RequiredArgsConstructor;
import my_project.mini_social_network.dto.CommentDto;
import my_project.mini_social_network.exceptions.ResourceNotFoundException;
import my_project.mini_social_network.models.Comment;
import my_project.mini_social_network.models.Post;
import my_project.mini_social_network.models.User;
import my_project.mini_social_network.repositories.CommentRepository;
import my_project.mini_social_network.repositories.PostRepository;
import my_project.mini_social_network.repositories.UserRepository;
import my_project.mini_social_network.services.CommentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public Comment saveComment(CommentDto commentDto) {
        User user = userRepository.findById(commentDto.getUserId()).orElseThrow(
                () -> new ResourceNotFoundException("User with id " + commentDto.getUserId() + " not found")
        );

        Post post = postRepository.findById(commentDto.getPostId()).orElseThrow(
                () -> new ResourceNotFoundException("Post with id " + commentDto.getPostId() + " not found")
        );

        Comment comment = new Comment();
        comment.setUser(user);
        comment.setPost(post);
        comment.setContent(commentDto.getContent());

        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> findByPost(Post post) {
        return commentRepository.findByPost(post);
    }

    @Override
    public List<Comment> findByUser(User user) {
        return commentRepository.findByUser(user);
    }

    @Override
    @Transactional
    public Comment updateComment(int commentId, CommentDto commentDto) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("Comment not found"));

        comment.setContent(commentDto.getContent());
        return commentRepository.save(comment);
    }

    @Override
    @Transactional
    public void deleteComment(int commentId) {
        commentRepository.deleteById(commentId);
    }

    @Override
    public Comment findByCommentId(int commentId) {
        return commentRepository.findById(commentId).orElse(null);
    }
}
