package my_project.mini_social_network.aspects;


import lombok.RequiredArgsConstructor;
import my_project.mini_social_network.annotations.IsAdmin;
import my_project.mini_social_network.exceptions.ResourceNotFoundException;
import my_project.mini_social_network.models.User;
import my_project.mini_social_network.repositories.UserRepository;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class AdminAspect {
    private final UserRepository userRepository;

    @Around("@annotation(isAdmin)")
    public Object checkAccess(ProceedingJoinPoint joinPoint, IsAdmin isAdmin) throws Throwable {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = userRepository.findByEmail(authentication.getName()).orElseThrow(
                () -> new ResourceNotFoundException("User not found")
        );

        if (currentUser.getRole().toString().equals("ADMIN")) {
            return joinPoint.proceed();
        } else {
            throw new AccessDeniedException("Access denied");
        }
    }
}
