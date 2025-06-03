package my_project.mini_social_network.controllers;

import lombok.RequiredArgsConstructor;
import my_project.mini_social_network.dto.UserRequest;
import my_project.mini_social_network.dto.UserResponse;
import my_project.mini_social_network.models.User;
import my_project.mini_social_network.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ModelMapper modelMapper;


    @PostMapping
    public UserResponse createUser(@RequestBody UserRequest requestUser) {
        return userService.saveUser(requestUser);
    }

    @PutMapping("/{id}")
    public UserResponse updateUser(@PathVariable int id, @RequestBody UserRequest requestUser) {
        return userService.updateUser(id, requestUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/email/{email}")
    public UserResponse findUserByEmail(@PathVariable String email) {
        return userService.findByEmail(email);
    }

    @GetMapping("/search")
    public List<UserResponse> searchUser(@RequestParam String query) {
        return userService.findByName(query);
    }

    @GetMapping
    public List<UserResponse> findAll() {
        return userService.findAll();
    }
}
