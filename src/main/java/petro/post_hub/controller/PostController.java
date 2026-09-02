package petro.post_hub.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import petro.post_hub.model.dto.PostDto;
import petro.post_hub.model.dto.PostSearchDto;
import petro.post_hub.model.request.NewPostRequest;
import petro.post_hub.model.request.PostSearchRequest;
import petro.post_hub.model.request.UpdatePostRequest;
import petro.post_hub.model.response.CustomResponse;
import petro.post_hub.model.response.PaginationResponse;
import petro.post_hub.security.CustomUserDetails;
import petro.post_hub.service.PostService;

@RequiredArgsConstructor
@RestController
@Validated
@RequestMapping("/post")
@Tag(name = "post", description = "post methods")
public class PostController {
    private final PostService postService;

    @GetMapping("/{id}")
    @Operation(summary = "get post by id",
            description = "get and return post by id")
    @ApiResponses(value =
    @ApiResponse(
            responseCode = "200",
            description = "successful get post by id",
            content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = """
                            {
                              "message": "success",
                              "body": {
                                "id": 3,
                                "title": "weather",
                                "content": "weather_of_Lviv",
                                "created": "2026-08-10T20:26:37.063501",
                                "updated": "2026-08-10T20:29:54.964453"
                              },
                              "success": true
                            }
                            """))
    ))
    public ResponseEntity<CustomResponse<PostDto>> getById(@PathVariable int id) {
        CustomResponse<PostDto> response = postService.getById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/save")
    @Operation(summary = "save post",
            description = "save and return post")
    @ApiResponses(value =
    @ApiResponse(
            responseCode = "200",
            description = "successful save post",
            content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = """
                            {
                              "message": "success",
                              "body": {
                                "id": 7,
                                "title": "petro save new",
                                "content": "save post",
                                "created": "2026-08-10T20:48:28.1207867",
                                "updated": "2026-08-10T20:48:28.1207867"
                              },
                              "success": true
                            }
                            """))
    ))
    public ResponseEntity<CustomResponse<PostDto>> save(@Valid @RequestBody NewPostRequest newPostRequest,
                                                        @AuthenticationPrincipal CustomUserDetails userDetails) {
        CustomResponse<PostDto> response = postService.save(newPostRequest, userDetails.getUser().getId());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/updated/{id}")
    @Operation(summary = "update post",
            description = "update and return post")
    @ApiResponses(value =
    @ApiResponse(
            responseCode = "200",
            description = "successful updated post",
            content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = """
                            {
                              "message": "success",
                              "body": {
                                "id": 4,
                                "title": "petro updated post",
                                "content": "updated post",
                                "created": "2026-08-10T20:34:22.599381",
                                "updated": "2026-08-10T20:52:40.5873668"
                              },
                              "success": true
                            }
                            """))
    ))
    public ResponseEntity<CustomResponse<PostDto>> updated(@Valid UpdatePostRequest updatePostRequest,
                                                           @AuthenticationPrincipal CustomUserDetails userDetails,
                                                           @PathVariable int id) {
        CustomResponse<PostDto> response = postService.updated(updatePostRequest, id, userDetails.getUser().getId());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "deleted post",
            description = "deleted and mark post as deleted")
    public ResponseEntity<Void> deleted(@PathVariable int id, @AuthenticationPrincipal CustomUserDetails userDetails) {
        postService.deleted(id, userDetails.getUser().getId());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/all")
    @Operation(summary = "get all",
            description = "get all with pagination")
    @ApiResponses(value =
    @ApiResponse(
            responseCode = "200",
            description = "successful get all posts",
            content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = """
                            {
                              "message": "success",
                              "body": {
                                "content": [
                                  {
                                    "id": 2,
                                    "title": "post_info",
                                    "content": "news_of_Sambir",
                                    "created": "2026-08-10T20:25:19.907322",
                                    "updated": "2026-08-10T20:25:19.907322",
                                    "deleted": false
                                  },
                                  {
                                    "id": 3,
                                    "title": "weather",
                                    "content": "weather_of_Lviv",
                                    "created": "2026-08-10T20:26:37.063501",
                                    "updated": "2026-08-10T20:29:54.964453",
                                    "deleted": false
                                  }
                                ],
                                "pagination": {
                                  "total": 7,
                                  "limit": 2,
                                  "page": 1,
                                  "pages": 4
                                }
                              },
                              "success": true
                            }
                            """))
    ))
    public ResponseEntity<CustomResponse<PaginationResponse<PostSearchDto>>> findPostAllByPagination(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "limit", defaultValue = "10") int limit
    ) {
        Pageable pageable = PageRequest.of(page, limit);
        CustomResponse<PaginationResponse<PostSearchDto>> response = postService.findAllPostByPagination(pageable);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    @Operation(summary = "get search",
            description = "get all with pagination and param")
    @ApiResponses(value =
    @ApiResponse(
            responseCode = "200",
            description = "successful all post with pagination and param",
            content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = """
                            {
                              "message": "success",
                              "body": {
                                "content": [
                                  {
                                    "id": 1,
                                    "title": "post_news",
                                    "content": "news_of_Lviv",
                                    "created": "2026-08-10T20:23:25.291847",
                                    "updated": "2026-08-10T20:23:25.291847",
                                    "deleted": true
                                  }
                                ],
                                "pagination": {
                                  "total": 1,
                                  "limit": 10,
                                  "page": 1,
                                  "pages": 1
                                }
                              },
                              "success": true
                            }
                            """)
            )

    ))
    public ResponseEntity<CustomResponse<PaginationResponse<PostSearchDto>>> findPostAllByPaginationWithParams(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "limit", defaultValue = "10") int limit,
            @RequestBody @Valid PostSearchRequest postSearchRequest
    ) {
        Pageable pageable = PageRequest.of(page, limit);
        CustomResponse<PaginationResponse<PostSearchDto>> response = postService.findAllPostByPaginationAndParams(pageable, postSearchRequest);
        return ResponseEntity.ok(response);
    }
}
