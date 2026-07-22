package petro.post_hub.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "posts")
public class Post implements Serializable {
    public static final String ID_FIELD_NAME = "id";
    public static final String TITLE_FIELD_NAME = "title";
    public static final String CONTENT_FIELD_NAME = "content";
    public static final String CREATED_FIELD_NAME = "created";
    public static final String UPDATED_FIELD_NAME = "updated";
    public static final String DELETED_FIELD_NAME = "deleted";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "created")
    private LocalDateTime created = LocalDateTime.now();

    @Column(name = "updated")
    private LocalDateTime updated = LocalDateTime.now();

    @Column(name = "deleted")
    private boolean deleted = false;
}
