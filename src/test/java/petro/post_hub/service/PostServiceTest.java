package petro.post_hub.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import petro.post_hub.mapper.PostMapper;
import petro.post_hub.model.constants.ApiErrorMessage;
import petro.post_hub.model.dto.PostDto;
import petro.post_hub.model.entity.Post;
import petro.post_hub.model.entity.User;
import petro.post_hub.model.exceptions.NotFoundException;
import petro.post_hub.repository.PostRepository;
import petro.post_hub.repository.UserRepository;
import petro.post_hub.security.validation.AccessValidator;
import petro.post_hub.service.impl.PostServiceImpl;

import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {
    @Mock
    private PostRepository postRepository;
    @Mock
    private PostMapper postMapper;
    @Mock
    private UserRepository userRepository;
    @Mock
    private AccessValidator accessValidator;
    @InjectMocks
    private PostServiceImpl postServiceImpl;

    private Post testPost;
    private PostDto testPostDto;
    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1);
        testUser.setUsername("testUser");

        testPost = new Post();
        testPost.setId(1);
        testPost.setTitle("testTitle");
        testPost.setContent("testContent");
        testPost.setUser(testUser);

        testPostDto = new PostDto();
        testPostDto.setId(1);
        testPostDto.setTitle("testTitle");
        testPostDto.setContent("testContent");


    }

    @Test
    void getById_PostExists_ReturnsPostDto() {
        when(postRepository.findByIdAndDeletedFalse(1)).thenReturn(Optional.of(testPost));
        when(postMapper.convertPostToPostDto(testPost)).thenReturn(testPostDto);
        PostDto result = postServiceImpl.getById(1).getBody();
        assertNotNull(result);
        assertEquals(testPostDto.getId(), result.getId());
        assertEquals(testPostDto.getTitle(), result.getTitle());
        assertEquals(testPostDto.getContent(), result.getContent());

        verify(postRepository, times(1)).findByIdAndDeletedFalse(1);
        verify(postMapper, times(1)).convertPostToPostDto(testPost);
    }

    @Test
    void getById_PostNotFound_ThrowsException() {
        when(postRepository.findByIdAndDeletedFalse(123456)).thenReturn(Optional.empty());
        NotFoundException exception = assertThrows(NotFoundException.class, () -> postServiceImpl.getById(123456));
        assertEquals(ApiErrorMessage.POST_NOT_FOUND_BY_ID.getMessage(123456), exception.getMessage());
        verify(postRepository, times(1)).findByIdAndDeletedFalse(123456);
        verify(postMapper, never()).convertPostToPostDto(any(Post.class));
    }

}
