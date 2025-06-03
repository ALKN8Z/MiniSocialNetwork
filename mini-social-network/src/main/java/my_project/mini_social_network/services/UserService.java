package my_project.mini_social_network.services;

import my_project.mini_social_network.dto.UserRequest;
import my_project.mini_social_network.dto.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse saveUser(UserRequest user);
    UserResponse updateUser(int id, UserRequest user);
    void deleteUser(int id);
    UserResponse findByEmail(String email);
    List<UserResponse> findByName(String name);
    List<UserResponse> findAll();
}
