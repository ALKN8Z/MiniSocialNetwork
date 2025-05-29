package my_project.mini_social_network.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CommentDto {

    @NotBlank(message = "Комментарий не может быть пустым!")
    private String content;

    @NotNull(message = "Id поста не может быть пустым!")
    private int postId;

    @NotNull(message = "Id автора комментария не может быть пустым!")
    private int userId;
}
