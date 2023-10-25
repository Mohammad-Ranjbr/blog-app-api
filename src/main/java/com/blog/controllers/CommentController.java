package com.blog.controllers;

import com.blog.payloads.ApiResponse;
import com.blog.payloads.CommentDto;
import com.blog.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService ;

    @Autowired
    public CommentController(CommentService commentService){
        this.commentService = commentService;
    }

    @PostMapping("/post/{postId}")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto, @PathVariable("postId") int post_id){
        CommentDto createdCommentDto = commentService.createComment(commentDto,post_id);
        return new ResponseEntity<>(createdCommentDto,HttpStatus.CREATED);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable("commentId") int comment_id){
        commentService.deleteComment(comment_id);
        return new ResponseEntity<>(new ApiResponse(String.format("Comment With ID : %d Deleted Successfully",comment_id),true),HttpStatus.OK);
    }

}
