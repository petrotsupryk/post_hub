package petro.post_hub.mapper.impl;

import org.springframework.stereotype.Component;
import petro.post_hub.mapper.CommentMapper;
import petro.post_hub.model.dto.CommentDto;
import petro.post_hub.model.dto.CommentSearchDto;
import petro.post_hub.model.entity.Comment;
import petro.post_hub.model.entity.Post;
import petro.post_hub.model.entity.User;
import petro.post_hub.model.request.NewCommentRequest;

@Component
public class CommentMapperImpl implements CommentMapper {

    @Override
    public CommentDto convertCommentToCommentDto(Comment comment) {
        return new CommentDto(comment.getId(), comment.getMessage(), comment.getUser().getId(), comment.getPost().getId(),
                comment.getCreated(), comment.getUpdated());
    }

    @Override
    public Comment convertNewCommentRequestToComment(NewCommentRequest newCommentRequest, User user, Post post) {
        Comment comment = new Comment();
        comment.setPost(post);
        comment.setUser(user);
        comment.setMessage(newCommentRequest.getMessage());

        return comment;
    }

    @Override
    public CommentSearchDto convertCommentToCommentSearchDto(Comment comment) {
        return new CommentSearchDto(comment.getId(), comment.getMessage(), comment.getUser().getId(), comment.getPost().getId(),
                comment.getCreated(), comment.getUpdated(), comment.isDeleted());
    }
}
