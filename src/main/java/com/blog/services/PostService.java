package com.blog.services;

import com.blog.payloads.PostDto;
import com.blog.payloads.PostResponse;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto , int userId , int categoryId);
    PostDto updatePost(PostDto postDto , int postId);
    void deletePost(int postId);
    PostDto getPost(int postId);
    PostResponse getAllPosts(int pageNumber , int pageSize , String sortBy , String sortDirection);
    List<PostDto> getPostsByCategory(int categoryId);
    List<PostDto> getPostsByUser(int userId);
    List<PostDto> searchPosts(String keyWord);

}
