package my_project.mini_social_network.security;

import lombok.RequiredArgsConstructor;
import my_project.mini_social_network.models.User;
import my_project.mini_social_network.repositories.UserRepository;
import my_project.mini_social_network.services.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with email - " + username + " not found"));

        return new CustomUserDetails(user);
    }
}
