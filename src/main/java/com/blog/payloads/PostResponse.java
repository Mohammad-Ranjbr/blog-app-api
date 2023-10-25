package com.blog.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class PostResponse {

    private List<PostDto> content ;
    private int pageNumber ;
    private int pageSize ;
    private Long totalElement ;
    private int totalPage ;
    private boolean lastPage ;

}
