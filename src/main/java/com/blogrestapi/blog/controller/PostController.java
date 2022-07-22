package com.blogrestapi.blog.controller;

import com.blogrestapi.blog.payload.PostDto;
import com.blogrestapi.blog.payload.PostResponse;
import com.blogrestapi.blog.service.PostService;
import com.blogrestapi.blog.untils.AppConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@Api(value = "CRUD Rest APIs for Post resources")
@RestController
@RequestMapping("/api/v1")
public class PostController {

    @Resource
    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @ApiOperation(value = "Create Post REST API")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/posts")
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto) {
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Get All Posts REST API")
    @GetMapping("/posts")
    public PostResponse getAllPosts(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ){
        return postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
    }

    @ApiOperation(value = "Get Post By Id REST API")
    @GetMapping("/posts/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(postService.getPostById(id), HttpStatus.OK);
    }

    @ApiOperation(value = "Update Post By Id REST API")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/posts/{id}")
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable(name = "id") Long id) {
        PostDto responsePost = postService.updatePost(postDto, id);

        return new ResponseEntity<>(responsePost, HttpStatus.OK);
    }

    @ApiOperation(value = "Delete Post By Id REST API")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/posts/{id}")
    public ResponseEntity<String> deletePost(@PathVariable(name = "id") Long id) {
        postService.deletePost(id);

        return new ResponseEntity<>("Delete successfully", HttpStatus.OK);
    }
}
