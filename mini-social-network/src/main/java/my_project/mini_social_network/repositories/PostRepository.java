package my_project.mini_social_network.repositories;

import my_project.mini_social_network.models.Post;
import my_project.mini_social_network.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findByUser(User user);
    List<Post> findPostsByTitleContaining(String title);
}
