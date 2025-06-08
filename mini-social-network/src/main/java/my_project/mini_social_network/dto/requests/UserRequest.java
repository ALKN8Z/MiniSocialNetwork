package my_project.mini_social_network.dto.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRequest {
    @NotBlank(message = "Имя пользователя не может быть пустым!")
    private String name;

    @NotBlank(message = "Email пользователя не может быть пустым!")
    @Email(message = "Введенный Email некорректен")
    private String email;

    @NotBlank(message = "Пароль не может быть пустым!")
    private String password;

}
