package my_project.mini_social_network.dto;

import lombok.Data;
import my_project.mini_social_network.models.Role;

@Data
public class UserResponse {
    private int id;
    private String name;
    private String email;
    private Role role;
}
