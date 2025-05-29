package my_project.mini_social_network.services;

import my_project.mini_social_network.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User saveUser(User user);
    Optional<User> findByEmail(String email);
    Optional<User> findByName(String name);
    List<User> findAll();
}
