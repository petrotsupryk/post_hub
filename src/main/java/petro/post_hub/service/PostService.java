package petro.post_hub.service;


import org.springframework.data.domain.Pageable;
import petro.post_hub.model.dto.PostDto;
import petro.post_hub.model.dto.PostSearchDto;
import petro.post_hub.model.request.NewPostRequest;
import petro.post_hub.model.request.PostSearchRequest;
import petro.post_hub.model.request.UpdatePostRequest;
import petro.post_hub.model.response.CustomResponse;
import petro.post_hub.model.response.PaginationResponse;


public interface PostService {
    CustomResponse<PostDto> getById(int id);

    CustomResponse<PostDto> save(NewPostRequest newPostRequest, int userId);

    CustomResponse<PostDto> updated(UpdatePostRequest updatePostRequest, int postId, int id);

    void deleted(int id, int userId);

    CustomResponse<PaginationResponse<PostSearchDto>> findAllPostByPagination(Pageable pageable);

    CustomResponse<PaginationResponse<PostSearchDto>> findAllPostByPaginationAndParams(Pageable pageable, PostSearchRequest postSearchRequest);
}
