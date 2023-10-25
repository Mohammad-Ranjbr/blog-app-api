package com.blog.services.Implementation;

import com.blog.entities.Comment;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.CommentDto;
import com.blog.entities.Post;
import com.blog.repositories.CommentRepository;
import com.blog.repositories.PostRepository;
import com.blog.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper ;
    @Autowired
    public CommentServiceImpl(PostRepository postRepository , CommentRepository commentRepository , ModelMapper modelMapper){
        this.postRepository =postRepository ;
        this.commentRepository = commentRepository ;
        this.modelMapper = modelMapper ;
    }
    @Override
    public CommentDto createComment(CommentDto commentDto, int postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post","id",String.valueOf(postId)));
        Comment comment = this.commentDtoToComment(commentDto);
        comment.setPost(post);
        Comment savedComment = commentRepository.save(comment);
        return this.commentToCommentDto(savedComment);
    }

    @Override
    public void deleteComment(int commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment","id",String.valueOf(commentId)));
        commentRepository.delete(comment);
    }

    public Comment commentDtoToComment(CommentDto commentDto){
        return modelMapper.map(commentDto,Comment.class);
    }

    public CommentDto commentToCommentDto(Comment comment){
        return  modelMapper.map(comment,CommentDto.class);
    }

}
