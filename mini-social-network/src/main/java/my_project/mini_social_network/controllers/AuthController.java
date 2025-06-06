package my_project.mini_social_network.controllers;


import lombok.RequiredArgsConstructor;
import my_project.mini_social_network.dto.requests.AuthRequest;
import my_project.mini_social_network.dto.responses.AuthResponse;
import my_project.mini_social_network.security.CustomUserDetails;
import my_project.mini_social_network.security.JwtUtils;
import my_project.mini_social_network.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final AuthService authService;

    private final JwtUtils jwtUtils;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody AuthRequest authRequest) {
        return ResponseEntity.ok().body(authService.registerUser(authRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody AuthRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(new
                    UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtUtils.generateToken((CustomUserDetails) authentication.getPrincipal());
            return ResponseEntity.ok(new AuthResponse(token));

        }catch (AuthenticationException exception){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exception.getMessage());
        }
    }
}
