package petro.post_hub.service;

import org.springframework.data.domain.Pageable;
import petro.post_hub.model.dto.CommentDto;
import petro.post_hub.model.dto.CommentSearchDto;
import petro.post_hub.model.request.CommentSearchRequest;
import petro.post_hub.model.request.NewCommentRequest;
import petro.post_hub.model.request.UpdateCommentRequest;
import petro.post_hub.model.response.CustomResponse;
import petro.post_hub.model.response.PaginationResponse;

public interface CommentsService {
    CustomResponse<CommentDto> getCommentById(int id);

    CustomResponse<CommentDto> createComment(NewCommentRequest newCommentRequest, int userId);

    CustomResponse<CommentDto> updateComment(int id, UpdateCommentRequest updateCommentRequest, int userId);

    void delete(int id, int userId);

    CustomResponse<PaginationResponse<CommentSearchDto>> findAllCommentsWithPagination(Pageable pageable);

    CustomResponse<PaginationResponse<CommentSearchDto>> findAllCommentsWithPaginationAndParams(CommentSearchRequest commentSearchRequest,
                                                                                                Pageable pageable);
}
