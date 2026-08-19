package petro.post_hub.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import petro.post_hub.model.entity.User;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentSearchDto implements Serializable {
    private int id;
    private String message;
    private int userId;
    private int postId;
    private LocalDateTime created;
    private LocalDateTime updated;
    private boolean deleted;
}
