package my_project.mini_social_network.services.impl;

import lombok.RequiredArgsConstructor;
import my_project.mini_social_network.repositories.UserRepository;
import my_project.mini_social_network.services.UserService;
import org.springframework.stereotype.Service;
import my_project.mini_social_network.models.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    @Transactional
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findByName(String name) {
        return userRepository.findByNameContaining(name);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

}
