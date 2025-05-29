package my_project.mini_social_network.repositories;

import my_project.mini_social_network.models.Comment;
import my_project.mini_social_network.models.Post;
import my_project.mini_social_network.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByPost(Post post);
    List<Comment> findByUser(User user);
}
