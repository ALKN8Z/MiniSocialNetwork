package my_project.mini_social_network.services.impl;

import lombok.RequiredArgsConstructor;
import my_project.mini_social_network.dto.requests.UserRequest;
import my_project.mini_social_network.dto.responses.UserResponse;
import my_project.mini_social_network.exceptions.ResourceNotFoundException;
import my_project.mini_social_network.models.Role;
import my_project.mini_social_network.repositories.UserRepository;
import my_project.mini_social_network.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import my_project.mini_social_network.models.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public UserResponse saveUser(UserRequest userRequest) {
        User newUser = User.builder()
                .name(userRequest.getName())
                .email(userRequest.getEmail())
                .role(Role.USER)
                .build();

        return modelMapper.map(userRepository.save(newUser), UserResponse.class);
    }

    @Override
    public UserResponse updateUser(int id, UserRequest userRequest) {
        User userToBeUpdated = User.builder()
                .id(id)
                .name(userRequest.getName())
                .email(userRequest.getEmail())
                .build();

        return modelMapper.map(userRepository.save(userToBeUpdated), UserResponse.class);
    }

    @Override
    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserResponse findByEmail(String email) {
        Optional<User> foundUser = userRepository.findByEmail(email);
        if (foundUser.isPresent()) {
            return modelMapper.map(foundUser.get(), UserResponse.class);
        } else {
            throw new ResourceNotFoundException("Пользователя с таким email не найдено!");
        }
    }

    @Override
    public List<UserResponse> findByName(String name) {
        List<User> foundUsers = userRepository.findByNameContaining(name);
        if (!foundUsers.isEmpty()) {
            return foundUsers.stream().map(user -> modelMapper.map(user, UserResponse.class)).toList();
        }else{
            throw new ResourceNotFoundException("Пользователь с таким именем не найден!");
        }
    }

    @Override
    public List<UserResponse> findAll() {
        return userRepository.findAll().stream().map(user ->
                modelMapper.map(user, UserResponse.class)).collect(Collectors.toList());
    }

    @Override
    public User findByUserId(int id) {
        return userRepository.findById(id).orElse(null);
    }

}
