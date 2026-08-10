package petro.post_hub.mapper.impl;

import org.springframework.stereotype.Component;
import petro.post_hub.mapper.PostMapper;
import petro.post_hub.model.dto.PostDto;
import petro.post_hub.model.dto.PostSearchDto;
import petro.post_hub.model.entity.Post;
import petro.post_hub.model.request.NewPostRequest;

@Component
public class PostMapperImpl implements PostMapper {
    @Override
    public PostDto convertPostToPostDto(Post post) {
        return new PostDto(post.getId(), post.getTitle(), post.getContent(), post.getCreated(), post.getUpdated());
    }

    @Override
    public Post convertNewPostDtoToPost(NewPostRequest newPostRequest) {
        Post post = new Post();
        post.setTitle(newPostRequest.getTitle());
        post.setContent(newPostRequest.getContent());
        return post;
    }

    @Override
    public PostSearchDto convertPostToPostSearchDto(Post post) {
        return new PostSearchDto(post.getId(), post.getTitle(), post.getContent(),
                post.getCreated(), post.getUpdated(), post.isDeleted());
    }
}
