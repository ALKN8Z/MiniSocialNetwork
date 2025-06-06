package my_project.mini_social_network.dto.responses;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PostResponse {
    private String title;
    private String content;
}
