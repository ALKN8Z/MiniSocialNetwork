package my_project.mini_social_network.dto.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthRequest {
    @NotBlank(message = "Логин не может быть пустым!")
    private String username;

    @NotBlank(message = "Email не может быть пустым!")
    @Email(message = "Введенный email невалиден!")
    private String email;

    @NotBlank(message = "Пароль не может быть пустым!")
    private String password;
}
