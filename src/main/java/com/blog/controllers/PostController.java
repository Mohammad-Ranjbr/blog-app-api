package com.blog.controllers;

import com.blog.config.AppConstans;
import com.blog.payloads.ApiResponse;
import com.blog.payloads.PostDto;
import com.blog.payloads.PostResponse;
import com.blog.services.FileService;
import com.blog.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService ;
    private final FileService fileService ;
    @Value("${project.image}")
    // @Value("${project.image}")
    private String path ;
    @Autowired
    public PostController(PostService postService,FileService fileService) {
        this.postService = postService;
        this.fileService =fileService;
    }

    // POST - Create Post
    @PostMapping("/user/{userId}/category/{categoryId}")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto
            , @PathVariable("userId") int user_id , @PathVariable("categoryId") int cat_id){
        PostDto post = postService.createPost(postDto,user_id,cat_id);
        return new ResponseEntity<>(post, HttpStatus.CREATED);
    }

    // PUT - Update Post
    @PutMapping("/{postId}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,@PathVariable("postId") int post_id){
        PostDto updatedPost = postService.updatePost(postDto,post_id);
        return new ResponseEntity<>(updatedPost,HttpStatus.OK);
    }
    // DELETE - Delete Post
    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponse> deletePostById(@PathVariable("postId") int post_id){
        postService.deletePost(post_id);
        return new ResponseEntity<>(new ApiResponse(String.format("Post With ID : %d Deleted Successfully",post_id),true),HttpStatus.OK);
    }
    // GET - Get By User
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable("userId") int user_id){
        List<PostDto> postDtos = postService.getPostsByUser(user_id);
        return  new ResponseEntity<>(postDtos,HttpStatus.OK);
    }

    // GET - Get By Category
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable("categoryId") int category_id){
        List<PostDto> postDtos = postService.getPostsByCategory(category_id);
        return new ResponseEntity<>(postDtos,HttpStatus.OK);
    }

    // GET - Get All Posts By Pagination And Sorting
//    @GetMapping("/posts")
//    public ResponseEntity<PostResponse> getAllPosts(@RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
//                                                    @RequestParam(value = "pageSize",defaultValue = "3",required = false) int pageSize,
//                                                    @RequestParam(value = "sortBy",defaultValue = "postId",required = false) String sortBy,
//                                                    @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDirection){ // sort direction
//        return new ResponseEntity<>(postService.getAllPosts(pageNumber,pageSize,sortBy,sortDirection),HttpStatus.OK);
//    }
    @GetMapping("")
    public ResponseEntity<PostResponse> getAllPosts(@RequestParam(value = "pageNumber",defaultValue = AppConstans.PAGE_NUMBER,required = false) int pageNumber,
                                                    @RequestParam(value = "pageSize",defaultValue = AppConstans.PAGE_SIZE,required = false) int pageSize,
                                                    @RequestParam(value = "sortBy",defaultValue = AppConstans.SORT_BY,required = false) String sortBy,
                                                    @RequestParam(value = "sortDir",defaultValue = AppConstans.SORT_DIR,required = false) String sortDirection){ // sort direction
        return new ResponseEntity<>(postService.getAllPosts(pageNumber,pageSize,sortBy,sortDirection),HttpStatus.OK);
    }
    // GET - Get Post By id
    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("postId") int post_id){
        return new ResponseEntity<>(postService.getPost(post_id),HttpStatus.OK);
    }

    // GET - Search
    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable("keyword") String key_word){
        return new ResponseEntity<>(postService.searchPosts(key_word),HttpStatus.OK);
    }

    // Post Image Upload
    @PostMapping("/image/upload/{postId}")
    public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image") MultipartFile multipartFile,
                                                         @PathVariable("postId") int post_id) throws IOException {
        String imageName = fileService.uploadImage(path,multipartFile);
        PostDto postDto = postService.getPost(post_id);
        postDto.setImageName(imageName);
        PostDto updatedPostDto = postService.updatePost(postDto,post_id);
        return new ResponseEntity<>(updatedPostDto,HttpStatus.OK);
    }

    // Download Image
    @GetMapping(value = "/image/download/{imageName}")
    public void downloadImage(@PathVariable("imageName") String image_name, HttpServletResponse httpServletResponse) throws IOException{
        InputStream inputStream = fileService.getImage(path,image_name);
        httpServletResponse.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(inputStream,httpServletResponse.getOutputStream());
    }

}
