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
import petro.post_hub.model.dto.CommentDto;
import petro.post_hub.model.dto.CommentSearchDto;
import petro.post_hub.model.request.CommentSearchRequest;
import petro.post_hub.model.request.NewCommentRequest;
import petro.post_hub.model.request.UpdateCommentRequest;
import petro.post_hub.model.response.CustomResponse;
import petro.post_hub.model.response.PaginationResponse;
import petro.post_hub.security.CustomUserDetails;
import petro.post_hub.service.CommentService;
import petro.post_hub.service.impl.CommentServiceImpl;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
@Validated
@Tag(name = "comment",
        description = "comments methods"
)
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/{id}")
    @Operation(summary = "get by id",
            description = "get comment by id")
    @ApiResponses(value =
    @ApiResponse(
            responseCode = "200",
            description = "successful comment by id",
            content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = """
                            {
                              "message": "success",
                              "body": {
                                "id": 2,
                                "message": "is sunny",
                                "ownerId": 4,
                                "postId": 9,
                                "created": "2026-09-02T14:17:44.392212",
                                "updated": "2026-09-02T14:17:44.392212"
                              },
                              "success": true
                            }
                            """))
    ))
    public ResponseEntity<CustomResponse<CommentDto>> getById(@PathVariable int id) {
        CustomResponse<CommentDto> response = commentService.getCommentById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/save")
    @Operation(summary = "create comment",
            description = "method for create comment")
    @ApiResponses(value =
    @ApiResponse(
            responseCode = "200",
            description = "successful created comment",
            content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = """
                            {
                              "message": "success",
                              "body": {
                                "id": 5,
                                "message": "comment from save",
                                "ownerId": 4,
                                "postId": 8,
                                "created": "2026-09-02T14:37:52.3680057",
                                "updated": "2026-09-02T14:37:52.3680057"
                              },
                              "success": true
                            }
                            """))
    ))
    public ResponseEntity<CustomResponse<CommentDto>> save(@Valid @RequestBody NewCommentRequest newCommentRequest,
                                                           @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        CustomResponse<CommentDto> response = commentService.createComment(newCommentRequest, customUserDetails.getUser().getId());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/updated/{id}")
    @Operation(summary = "update comment",
            description = "update comment by id")
    @ApiResponses(value =
    @ApiResponse(
            responseCode = "200",
            description = "successful update comment",
            content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = """
                            {
                              "message": "success",
                              "body": {
                                "id": 2,
                                "message": "updated comment 2",
                                "ownerId": 4,
                                "postId": 9,
                                "created": "2026-09-02T14:17:44.392212",
                                "updated": "2026-09-02T14:17:44.392212"
                              },
                              "success": true
                            }
                            """))
    ))
    public ResponseEntity<CustomResponse<CommentDto>> update(@Valid @RequestBody UpdateCommentRequest updateCommentRequest,
                                                             @AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                             @PathVariable int id) {
        CustomResponse<CommentDto> response = commentService.updateComment(id, updateCommentRequest, customUserDetails.getUser().getId());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "delete comment",
            description = "delete comment by id")
    @ApiResponses(value =
    @ApiResponse(
            responseCode = "200",
            description = "successful delete comment"
    ))
    public ResponseEntity<Void> delete(@PathVariable int id,
                                       @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        commentService.delete(id, customUserDetails.getUser().getId());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/all")
    @Operation(summary = "get all comments",
            description = "get all comment with pagination")
    @ApiResponses(value =
    @ApiResponse(
            responseCode = "200",
            description = "successful get all",
            content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = """
                            {
                              "message": "success",
                              "body": {
                                "content": [
                                  {
                                    "id": 4,
                                    "message": "comment from post id 8",
                                    "userId": 5,
                                    "postId": 8,
                                    "created": "2026-09-02T14:25:08.68363",
                                    "updated": "2026-09-02T14:25:08.68363",
                                    "deleted": false
                                  },
                                  {
                                    "id": 3,
                                    "message": "comment from post id 9",
                                    "userId": 5,
                                    "postId": 9,
                                    "created": "2026-09-02T14:23:32.031288",
                                    "updated": "2026-09-02T14:23:32.031288",
                                    "deleted": true
                                  },
                                  {
                                    "id": 1,
                                    "message": "warning",
                                    "userId": 4,
                                    "postId": 8,
                                    "created": "2026-09-02T14:16:08.05029",
                                    "updated": "2026-09-02T14:16:08.05029",
                                    "deleted": true
                                  },
                                  {
                                    "id": 5,
                                    "message": "comment from save",
                                    "userId": 4,
                                    "postId": 8,
                                    "created": "2026-09-02T14:37:52.368006",
                                    "updated": "2026-09-02T14:37:52.368006",
                                    "deleted": false
                                  },
                                  {
                                    "id": 2,
                                    "message": "updated comment 2",
                                    "userId": 4,
                                    "postId": 9,
                                    "created": "2026-09-02T14:17:44.392212",
                                    "updated": "2026-09-02T14:17:44.392212",
                                    "deleted": true
                                  }
                                ],
                                "pagination": {
                                  "total": 5,
                                  "limit": 10,
                                  "page": 1,
                                  "pages": 1
                                }
                              },
                              "success": true
                            }
                            """))
    ))
    public ResponseEntity<CustomResponse<PaginationResponse<CommentSearchDto>>> findAllCommentsByPagination(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "limit", defaultValue = "10") int limit
    ) {
        Pageable pageable = PageRequest.of(page, limit);
        CustomResponse<PaginationResponse<CommentSearchDto>> response = commentService.findAllCommentsWithPagination(pageable);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    @Operation(summary = "search comment",
            description = "search comment by pagination and param")
    @ApiResponses(value =
    @ApiResponse(
            responseCode = "200",
            description = "successful get all comments with pagination and param",
            content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = """
                            {
                              "message": "success",
                              "body": {
                                "content": [
                                  {
                                    "id": 5,
                                    "message": "comment from save",
                                    "userId": 4,
                                    "postId": 8,
                                    "created": "2026-09-02T14:37:52.368006",
                                    "updated": "2026-09-02T14:37:52.368006",
                                    "deleted": false
                                  },
                                  {
                                    "id": 4,
                                    "message": "comment from post id 8",
                                    "userId": 5,
                                    "postId": 8,
                                    "created": "2026-09-02T14:25:08.68363",
                                    "updated": "2026-09-02T14:25:08.68363",
                                    "deleted": false
                                  }
                                ],
                                "pagination": {
                                  "total": 2,
                                  "limit": 10,
                                  "page": 1,
                                  "pages": 1
                                }
                              },
                              "success": true
                            }
                            """))
    ))
    public ResponseEntity<CustomResponse<PaginationResponse<CommentSearchDto>>> findAllCommentsByPaginationAndParam(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "limit", defaultValue = "10") int limit,
            @RequestBody @Valid CommentSearchRequest commentSearchRequest
    ) {
        Pageable pageable = PageRequest.of(page, limit);
        CustomResponse<PaginationResponse<CommentSearchDto>> response = commentService.findAllCommentsWithPaginationAndParams(
                commentSearchRequest, pageable);
        return ResponseEntity.ok(response);
    }
}
