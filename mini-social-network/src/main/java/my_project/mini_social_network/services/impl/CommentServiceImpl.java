package my_project.mini_social_network.services.impl;

import lombok.RequiredArgsConstructor;
import my_project.mini_social_network.dto.CommentDto;
import my_project.mini_social_network.dto.CommentRequest;
import my_project.mini_social_network.exceptions.ResourceNotFoundException;
import my_project.mini_social_network.models.Comment;
import my_project.mini_social_network.models.Post;
import my_project.mini_social_network.models.User;
import my_project.mini_social_network.repositories.CommentRepository;
import my_project.mini_social_network.services.CommentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    @Override
    @Transactional
    public Comment saveComment(Comment comment) {
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
}
