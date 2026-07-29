package petro.post_hub.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import petro.post_hub.mapper.PostMapper;
import petro.post_hub.model.constants.ApiErrorMessage;
import petro.post_hub.model.dto.PostDto;
import petro.post_hub.model.dto.PostSearchDto;
import petro.post_hub.model.entity.Post;
import petro.post_hub.model.exceptions.NotFoundException;
import petro.post_hub.model.request.NewPostRequest;
import petro.post_hub.model.request.PostSearchRequest;
import petro.post_hub.model.request.UpdatePostRequest;
import petro.post_hub.model.response.CustomResponse;
import petro.post_hub.model.response.PaginationResponse;
import petro.post_hub.repository.PostRepository;
import petro.post_hub.repository.UserRepository;
import petro.post_hub.security.validation.AccessValidator;
import petro.post_hub.service.PostService;

import java.awt.print.Pageable;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final UserRepository userRepository;
    private final AccessValidator accessValidator;

    @Override
    public CustomResponse<PostDto> getById(int id) {
        Post post = postRepository.findByIdAndDeletedFalse(id).orElseThrow(() ->
                new NotFoundException(ApiErrorMessage.POST_NOT_FOUND_BY_ID.getMessage(id)));
        PostDto postDto = postMapper.convertPostToPostDto(post);
        return CustomResponse.success(postDto);
    }

    @Override
    public CustomResponse<PostDto> save(NewPostRequest newPostRequest, int userId) {
        return null;
    }

    @Override
    public CustomResponse<PostDto> updated(UpdatePostRequest updatePostRequest, int postId, int id) {
        return null;
    }

    @Override
    public void deleted(int id) {
        Post post = postRepository.findByIdAndDeletedFalse(id).orElseThrow(() ->
                new NotFoundException(ApiErrorMessage.POST_NOT_FOUND_BY_ID.getMessage(id)));
        accessValidator.validateAdminOrOwnerAccess();
    }

    @Override
    public CustomResponse<PaginationResponse<PostSearchDto>> findAllPostByPagination(Pageable pageable) {
        return null;
    }

    @Override
    public CustomResponse<PaginationResponse<PostSearchDto>> findAllPostByPaginationAndParams(Pageable pageable, PostSearchRequest postSearchRequest) {
        return null;
    }
}
