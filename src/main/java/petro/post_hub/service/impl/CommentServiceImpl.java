package petro.post_hub.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import petro.post_hub.mapper.CommentMapper;
import petro.post_hub.model.constants.ApiErrorMessage;
import petro.post_hub.model.dto.CommentDto;
import petro.post_hub.model.dto.CommentSearchDto;
import petro.post_hub.model.entity.Comment;
import petro.post_hub.model.entity.Post;
import petro.post_hub.model.entity.User;
import petro.post_hub.model.exceptions.NotFoundException;
import petro.post_hub.model.request.CommentSearchRequest;
import petro.post_hub.model.request.NewCommentRequest;
import petro.post_hub.model.request.UpdateCommentRequest;
import petro.post_hub.model.response.CustomResponse;
import petro.post_hub.model.response.PaginationResponse;
import petro.post_hub.repository.CommentRepository;
import petro.post_hub.repository.PostRepository;
import petro.post_hub.repository.UserRepository;
import petro.post_hub.repository.criteria.CommentSearchCriteria;
import petro.post_hub.security.validation.AccessValidator;
import petro.post_hub.service.CommentService;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final AccessValidator accessValidator;

    @Override
    public CustomResponse<CommentDto> getCommentById(int id) {
        Comment comment = commentRepository.findByIdAndDeletedFalse(id).orElseThrow(() ->
                new NotFoundException(ApiErrorMessage.COMMENT_NOT_FOUND_BY_ID.getMessage(id)));
        CommentDto commentDto = commentMapper.convertCommentToCommentDto(comment);
        return CustomResponse.success(commentDto);
    }

    @Override
    public CustomResponse<CommentDto> createComment(NewCommentRequest newCommentRequest, int userId) {
        User user = userRepository.findByIdAndDeletedFalse(userId).orElseThrow(() ->
                new NotFoundException(ApiErrorMessage.USER_NOT_FOUND_BY_ID.getMessage(userId)));
        Post post = postRepository.findByIdAndDeletedFalse(newCommentRequest.getPostId()).orElseThrow(() ->
                new NotFoundException(ApiErrorMessage.POST_NOT_FOUND_BY_ID.getMessage(newCommentRequest.getPostId())));
        Comment comment = commentMapper.convertNewCommentRequestToComment(newCommentRequest, user, post);
        comment = commentRepository.save(comment);
        CommentDto commentDto = commentMapper.convertCommentToCommentDto(comment);
        return CustomResponse.success(commentDto);
    }

    @Override
    public CustomResponse<CommentDto> updateComment(int id, UpdateCommentRequest updateCommentRequest, int userId) {
        Comment comment = commentRepository.findByIdAndDeletedFalse(id).orElseThrow(() ->
                new NotFoundException(ApiErrorMessage.COMMENT_NOT_FOUND_BY_ID.getMessage(id)));
        accessValidator.validateAdminOrOwnerAccess(comment.getUser().getId(), userId);
        comment.setMessage(updateCommentRequest.getMessage());
        comment = commentRepository.save(comment);
        CommentDto commentDto = commentMapper.convertCommentToCommentDto(comment);
        return CustomResponse.success(commentDto);
    }

    @Override
    public void delete(int id, int userId) {
        Comment comment = commentRepository.findByIdAndDeletedFalse(id).orElseThrow(() ->
                new NotFoundException(ApiErrorMessage.COMMENT_NOT_FOUND_BY_ID.getMessage(id)));
        accessValidator.validateAdminOrOwnerAccess(comment.getUser().getId(), userId);
        comment.setDeleted(true);
        commentRepository.save(comment);
    }

    @Override
    public CustomResponse<PaginationResponse<CommentSearchDto>> findAllCommentsWithPagination(Pageable pageable) {
        Page<CommentSearchDto> comments = commentRepository.findAll(pageable)
                .map(commentMapper::convertCommentToCommentSearchDto);
        PaginationResponse<CommentSearchDto> paginationResponse = new PaginationResponse<>(
                comments.getContent(),
                new PaginationResponse.Pagination(
                        comments.getTotalElements(),
                        pageable.getPageSize(),
                        comments.getNumber() + 1,
                        comments.getTotalPages()
                )
        );
        return CustomResponse.success(paginationResponse);
    }

    @Override
    public CustomResponse<PaginationResponse<CommentSearchDto>> findAllCommentsWithPaginationAndParams(CommentSearchRequest commentSearchRequest, Pageable pageable) {
        Specification<Comment> specification = new CommentSearchCriteria(commentSearchRequest);
        Page<CommentSearchDto> comments = commentRepository.findAll(specification, pageable)
                .map(commentMapper::convertCommentToCommentSearchDto);
        PaginationResponse<CommentSearchDto> paginationResponse = new PaginationResponse<>(
                comments.getContent(),
                new PaginationResponse.Pagination(
                        comments.getTotalElements(),
                        pageable.getPageSize(),
                        comments.getNumber() + 1,
                        comments.getTotalPages()
                )
        );
        return CustomResponse.success(paginationResponse);
    }
}
