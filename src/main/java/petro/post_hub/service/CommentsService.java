package petro.post_hub.service;

import petro.post_hub.model.dto.CommentDto;
import petro.post_hub.model.request.NewCommentRequest;
import petro.post_hub.model.response.CustomResponse;

public interface CommentsService {
    CustomResponse<CommentDto>getCommentById(int id);
   // CustomResponse<CommentDto>createComment(NewCommentRequest newCommentRequest, int postId);
}
