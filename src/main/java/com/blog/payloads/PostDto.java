package com.blog.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {

    private int postId ;
    private String title ;
    private String content ;
    private String imageName ;
    private Date createdDate ;
    private CategoryDto category ;
    private UserDto user ;
    private List<CommentDto> comments ;

}
