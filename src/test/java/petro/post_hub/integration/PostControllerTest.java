package petro.post_hub.integration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.hibernate.Hibernate;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import petro.post_hub.SecurityApplication;
import petro.post_hub.model.dto.PostDto;
import petro.post_hub.model.entity.Post;
import petro.post_hub.model.entity.User;
import petro.post_hub.model.exceptions.InvalidDataException;
import petro.post_hub.model.response.CustomResponse;
import petro.post_hub.repository.UserRepository;
import petro.post_hub.security.JwtTokenProvider;

import java.io.IOException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest(classes = SecurityApplication.class)
@AutoConfigureMockMvc
@ExtendWith({SpringExtension.class, MockitoExtension.class})
public class PostControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserRepository userRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private String adminJwt;
    private String userJwt;

    @BeforeAll
    void authorize() {
        User admin = userRepository.findByIdAndDeletedFalse(1).orElseThrow(() -> new InvalidDataException("admin with id 1 not found"));
        // Hibernate.initialize(admin.getRoles());
        this.adminJwt = "Bearer " + jwtTokenProvider.generatedToken(admin);

        User user = userRepository.findByIdAndDeletedFalse(2).orElseThrow(() -> new InvalidDataException("user with id 2 not found"));
        // Hibernate.initialize(admin.getRoles());
        this.userJwt = "Bearer " + jwtTokenProvider.generatedToken(user);
    }

    @Test
    void getPosts_Ok_200() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/post/all")
                                .header("Authorization", userJwt)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getPostById_Ok_200() throws Exception {
        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/post/2")
                                .header("Authorization", userJwt)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        CustomResponse<PostDto> response = convertResponseToPostDto(result.getResponse().getContentAsByteArray());
        assertTrue(response.isSuccess());

        PostDto postDto = Objects.nonNull(response.getBody()) ? response.getBody() : null;
        assertNotNull(postDto);
        assertEquals("post_info",postDto.getTitle());
        assertEquals("news_of_Sambir",postDto.getContent());

    }

    private CustomResponse<PostDto> convertResponseToPostDto(byte[] content) throws IOException {
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper.readValue(content, new TypeReference<>() {
        });
    }
}
