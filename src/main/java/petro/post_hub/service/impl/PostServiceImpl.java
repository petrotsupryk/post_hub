package petro.post_hub.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import petro.post_hub.mapper.PostMapper;
import petro.post_hub.model.constants.ApiErrorMessage;
import petro.post_hub.model.dto.PostDto;
import petro.post_hub.model.dto.PostSearchDto;
import petro.post_hub.model.entity.Post;
import petro.post_hub.model.entity.User;
import petro.post_hub.model.exceptions.DataExistsException;
import petro.post_hub.model.exceptions.NotFoundException;
import petro.post_hub.model.request.NewPostRequest;
import petro.post_hub.model.request.PostSearchRequest;
import petro.post_hub.model.request.UpdatePostRequest;
import petro.post_hub.model.response.CustomResponse;
import petro.post_hub.model.response.PaginationResponse;
import petro.post_hub.repository.PostRepository;
import petro.post_hub.repository.UserRepository;
import petro.post_hub.repository.criteria.PostSearchCriteria;
import petro.post_hub.security.validation.AccessValidator;
import petro.post_hub.service.PostService;

import java.time.LocalDateTime;

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
        if (postRepository.existsByTitle(newPostRequest.getTitle())) {
            throw new DataExistsException(ApiErrorMessage.POST_WITH_THIS_TITLE_ALREADY_EXISTS.getMessage(newPostRequest));
        }
        User user = userRepository.findByIdAndDeletedFalse(userId).orElseThrow(() ->
                new NotFoundException(ApiErrorMessage.USER_NOT_FOUND_BY_ID.getMessage(userId)));
        Post post = postMapper.convertNewPostDtoToPost(newPostRequest);
        post.setUser(user);
        post = postRepository.save(post);
        PostDto postDto = postMapper.convertPostToPostDto(post);
        return CustomResponse.success(postDto);
    }

    @Override
    public CustomResponse<PostDto> updated(UpdatePostRequest updatePostRequest, int postId, int userId) {
        Post post = postRepository.findByIdAndDeletedFalse(postId).orElseThrow(() ->
                new NotFoundException(ApiErrorMessage.POST_NOT_FOUND_BY_ID.getMessage(postId)));
        accessValidator.validateAdminOrOwnerAccess(post.getUser().getId(), userId);
        post.setTitle(updatePostRequest.getTitle());
        post.setContent(updatePostRequest.getContent());
        post.setUpdated(LocalDateTime.now());
        post = postRepository.save(post);
        PostDto postDto = postMapper.convertPostToPostDto(post);
        return CustomResponse.success(postDto);
    }

    @Override
    public void deleted(int id, int userId) {
        Post post = postRepository.findByIdAndDeletedFalse(id).orElseThrow(() ->
                new NotFoundException(ApiErrorMessage.POST_NOT_FOUND_BY_ID.getMessage(id)));
        accessValidator.validateAdminOrOwnerAccess(post.getUser().getId(), userId);
        post.setDeleted(true);
        postRepository.save(post);
    }

    @Override
    public CustomResponse<PaginationResponse<PostSearchDto>> findAllPostByPagination(Pageable pageable) {
        Page<PostSearchDto> posts = postRepository.findAll(pageable).map(postMapper::convertPostToPostSearchDto);
        PaginationResponse<PostSearchDto> paginationResponse = new PaginationResponse<>(
                posts.getContent(),
                new PaginationResponse.Pagination(
                        posts.getTotalElements(),
                        pageable.getPageSize(),
                        pageable.getPageNumber() + 1,
                        posts.getTotalPages()
                )
        );
        return CustomResponse.success(paginationResponse);
    }

    @Override
    public CustomResponse<PaginationResponse<PostSearchDto>> findAllPostByPaginationAndParams(Pageable pageable, PostSearchRequest postSearchRequest) {
        Specification<Post> specification = new PostSearchCriteria(postSearchRequest);
        Page<PostSearchDto> posts = postRepository.findAll(specification, pageable).map(postMapper::convertPostToPostSearchDto);
        PaginationResponse<PostSearchDto> paginationResponse = new PaginationResponse<>(
                posts.getContent(),
                new PaginationResponse.Pagination(
                        posts.getTotalElements(),
                        pageable.getPageSize(),
                        pageable.getPageNumber() + 1,
                        posts.getTotalPages()
                )
        );
        return CustomResponse.success((paginationResponse));
    }
}
