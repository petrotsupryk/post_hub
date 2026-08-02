package petro.post_hub.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import petro.post_hub.model.dto.PostDto;
import petro.post_hub.model.response.CustomResponse;
import petro.post_hub.service.PostService;

@RequiredArgsConstructor
@RestController
@Validated
@Slf4j
@RequestMapping("/post")
public class PostController {
    private final PostService postService;

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse<PostDto>> getById(@PathVariable int id) {
        CustomResponse<PostDto> response = postService.getById(id);
        return ResponseEntity.ok(response);
    }
}
