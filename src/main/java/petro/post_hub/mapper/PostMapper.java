package petro.post_hub.mapper;

import petro.post_hub.model.dto.PostDto;
import petro.post_hub.model.dto.PostSearchDto;
import petro.post_hub.model.entity.Post;
import petro.post_hub.model.request.NewPostRequest;


public interface PostMapper {
    PostDto convertPostToPostDto(Post post);

    Post convertNewPostDtoToPost(NewPostRequest newPostRequest);

    PostSearchDto convertPostToPostSearchDto(Post post);

}
