package petro.post_hub.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewCommentRequest implements Serializable {

    @NotBlank(message = "postId must be present")
    private int postId;

    @NotBlank(message = "message must be present")
    @Size(min = 5, max = 100, message = "message must be from 5 to 100")
    private String message;
}
