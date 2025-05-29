package my_project.mini_social_network.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PostDto {

    @NotBlank(message = "Title should be not blank")
    private String title;

    @NotBlank(message = "Content should be not blank")
    private String content;

    @NotNull(message = "User Id should be not null")
    private int userId;
}
