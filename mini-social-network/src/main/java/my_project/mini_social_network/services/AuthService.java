package my_project.mini_social_network.services;

import my_project.mini_social_network.dto.requests.AuthRequest;
import my_project.mini_social_network.dto.responses.AuthResponse;

public interface AuthService {
    AuthResponse registerUser(AuthRequest authRequest);
}
