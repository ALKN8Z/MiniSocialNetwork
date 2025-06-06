package my_project.mini_social_network.aspects;


import lombok.RequiredArgsConstructor;
import my_project.mini_social_network.annotations.IsOwnerOrAdmin;
import my_project.mini_social_network.dto.requests.UserRequest;
import my_project.mini_social_network.exceptions.ResourceNotFoundException;
import my_project.mini_social_network.models.Comment;
import my_project.mini_social_network.models.Post;
import my_project.mini_social_network.models.User;
import my_project.mini_social_network.repositories.UserRepository;
import my_project.mini_social_network.services.CommentService;
import my_project.mini_social_network.services.PostService;
import my_project.mini_social_network.services.UserService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.ApplicationContext;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class OwnerOrAdminAspect {

    private final UserRepository userRepository;
    private final ApplicationContext applicationContext;

    @Around("@annotation(isOwnerOrAdmin)")
    public Object checkAccess(ProceedingJoinPoint joinPoint, IsOwnerOrAdmin isOwnerOrAdmin) throws Throwable {

        // достаем из контекста аутентификацию и по email находим пользователя
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = userRepository.findByEmail(authentication.getName()).orElseThrow(
                () -> new ResourceNotFoundException("User not found")
        );

        // если у найденного пользователя роль админ -> доступ разрешен и метод выполняется
        if (currentUser.getRole().toString().equals("ADMIN")) {
            return joinPoint.proceed();
        }

        // получаем аргументы метода над которым аннотация
        Object[] args = joinPoint.getArgs();
        // индекс параметра метода, который хранит id ресурса, требующего изменения
        int indexOfResourceIdParam = isOwnerOrAdmin.indexOfParameterContainsResourceId();
        // из контекста приложения достаем бин класса на котором нужно вызвать метод для нахождения ресурса по id
        Object service = applicationContext.getBean(isOwnerOrAdmin.classOfRequiredMethod());
        // название метода который найдет ресурс по id
        String methodName = isOwnerOrAdmin.methodName();
        // id ресурса по началу null
        Integer resourceId = null;

        // извлекаем id ресурса из параметра под индексом indexOfResourceIdParam
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof Integer && i == indexOfResourceIdParam) {
                resourceId = (Integer) args[i];
            }
        }

        if (resourceId == null) {
            throw new ResourceNotFoundException("Resource id not found in method parameters");
        }

        // вызываем метод на нужном классе и получаем ресурс по resourceId
        Object resource = service.getClass().getMethod(methodName, int.class)
                .invoke(service, resourceId);


        // проверяем чтобы id currentUser'а совпал с id владельца ресурса
        if (resource instanceof Post){
            if (((Post) resource).getUser().getId() != currentUser.getId()){
                throw new AccessDeniedException("Access denied");
            }
        } else if (resource instanceof Comment){
            if (((Comment) resource).getUser().getId() != currentUser.getId()){
                throw new AccessDeniedException("Access denied");
            }
        } else {
            if (((User) resource).getId() != currentUser.getId()){
                throw new AccessDeniedException("Access denied");
            }
        }

        return joinPoint.proceed();


    }
}
