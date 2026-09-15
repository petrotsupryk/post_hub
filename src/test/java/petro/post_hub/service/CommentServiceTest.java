package petro.post_hub.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import petro.post_hub.mapper.CommentMapper;
import petro.post_hub.model.constants.ApiErrorMessage;
import petro.post_hub.model.dto.CommentDto;
import petro.post_hub.model.entity.Comment;
import petro.post_hub.model.entity.Post;
import petro.post_hub.model.entity.User;
import petro.post_hub.model.exceptions.NotFoundException;
import petro.post_hub.repository.CommentRepository;
import petro.post_hub.repository.PostRepository;
import petro.post_hub.repository.UserRepository;
import petro.post_hub.security.validation.AccessValidator;
import petro.post_hub.service.impl.CommentServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

public class CommentServiceTest {
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private CommentMapper commentMapper;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PostRepository postRepository;
    @Mock
    private AccessValidator accessValidator;
    @InjectMocks
    private CommentServiceImpl commentServiceImpl;

    private User testUser;
    private Post testPost;
    private Comment testComment;
    private CommentDto testCommentDto;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1);
        testUser.setUsername("testUsername");

        testPost = new Post();
        testPost.setId(1);
        testPost.setTitle("testTitle");

        testComment = new Comment();
        testComment.setId(1);
        testComment.setMessage("testMessage");
        testComment.setUser(testUser);
        testComment.setPost(testPost);

        testCommentDto = new CommentDto();
        testCommentDto.setId(1);
        testCommentDto.setMessage("testMessage");
    }

    @Test
    void getByIdComment_Ok() {
        when(commentRepository.findByIdAndDeletedFalse(1)).thenReturn(Optional.of(testComment));
        when(commentMapper.convertCommentToCommentDto(testComment)).thenReturn(testCommentDto);

        CommentDto response = commentServiceImpl.getCommentById(1).getBody();

        assertNotNull(response);

        assertEquals(testCommentDto.getId(), response.getId());
        assertEquals(testCommentDto.getMessage(), response.getMessage());

        verify(commentRepository, times(1)).findByIdAndDeletedFalse(1);
        verify(commentMapper, times(1)).convertCommentToCommentDto(testComment);
    }

    @Test
    void getByIdComment_NotFound_NotFoundException() {
        when(commentRepository.findByIdAndDeletedFalse(1)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () ->
                commentServiceImpl.getCommentById(1));
        assertEquals(ApiErrorMessage.COMMENT_NOT_FOUND_BY_ID.getMessage(1), exception.getMessage());

        verify(commentRepository, times(1)).findByIdAndDeletedFalse(1);
        verify(commentMapper, never()).convertCommentToCommentDto(testComment);
    }

}
