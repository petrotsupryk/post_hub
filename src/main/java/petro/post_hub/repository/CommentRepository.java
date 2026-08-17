package petro.post_hub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import petro.post_hub.model.entity.Comments;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comments, Integer>, JpaSpecificationExecutor<Comments> {
    Optional<Comments> findByIdAndDeletedFalse(int id);
}
