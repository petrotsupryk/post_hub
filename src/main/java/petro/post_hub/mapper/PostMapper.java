package petro.post_hub.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import petro.post_hub.model.dto.PostDto;
import petro.post_hub.model.dto.PostSearchDto;
import petro.post_hub.model.entity.Post;
import petro.post_hub.model.request.NewPostRequest;
import petro.post_hub.model.response.CustomResponse;


@Mapper(
        componentModel = "string",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        imports = {Object.class}
)
public interface PostMapper {
    PostDto convertPostToPostDto(Post post);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "updated", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    Post convertNewPostDtoToPost(NewPostRequest newPostRequest);

    PostSearchDto convertPostToPostSearchDto(Post post);

}
