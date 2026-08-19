package petro.post_hub.mapper;

import petro.post_hub.model.dto.CommentDto;
import petro.post_hub.model.dto.CommentSearchDto;
import petro.post_hub.model.entity.Comment;
import petro.post_hub.model.entity.Post;
import petro.post_hub.model.entity.User;
import petro.post_hub.model.request.NewCommentRequest;

public interface CommentMapper {
    CommentDto convertCommentToCommentDto(Comment comment);

    Comment convertNewCommentRequestToComment(NewCommentRequest newCommentRequest, User user, Post post);

    CommentSearchDto convertCommentToCommentSearchDto(Comment comment);
}
