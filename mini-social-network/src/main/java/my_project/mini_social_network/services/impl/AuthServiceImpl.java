package my_project.mini_social_network.services.impl;

import lombok.RequiredArgsConstructor;
import my_project.mini_social_network.dto.requests.AuthRequest;
import my_project.mini_social_network.dto.responses.AuthResponse;
import my_project.mini_social_network.exceptions.ConflictException;
import my_project.mini_social_network.models.Role;
import my_project.mini_social_network.models.User;
import my_project.mini_social_network.repositories.UserRepository;
import my_project.mini_social_network.security.CustomUserDetails;
import my_project.mini_social_network.security.JwtUtils;
import my_project.mini_social_network.services.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public AuthResponse registerUser(AuthRequest authRequest) {
        if (userRepository.findByEmail(authRequest.getEmail()).isPresent()) {
            throw new ConflictException("this email already exists!");

        }

        User newUser =  User.builder()
                .name(authRequest.getUsername())
                .email(authRequest.getEmail())
                .password(passwordEncoder.encode(authRequest.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(newUser);

        String token = jwtUtils.generateToken(new CustomUserDetails(newUser));

        return new AuthResponse(token);
    }
}
