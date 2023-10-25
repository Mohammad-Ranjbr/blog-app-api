package com.blog.services.Implementation;

import com.blog.entities.Category;
import com.blog.entities.Post;
import com.blog.entities.User;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.PostDto;
import com.blog.payloads.PostResponse;
import com.blog.repositories.CategoryRepository;
import com.blog.repositories.PostRepository;
import com.blog.repositories.UserRepository;
import com.blog.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository ;
    private final UserRepository userRepository ;
    private final CategoryRepository categoryRepository ;
    private final ModelMapper modelMapper ;
    @Autowired
    public PostServiceImpl(PostRepository postRepository , ModelMapper modelMapper ,
                           UserRepository userRepository , CategoryRepository categoryRepository){
        this.postRepository = postRepository ;
        this.modelMapper = modelMapper ;
        this.userRepository =userRepository ;
        this.categoryRepository =categoryRepository ;
    }
    @Override
    public PostDto createPost(PostDto postDto , int userId , int categoryId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User","id",String.valueOf(userId)));
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category","id",String.valueOf(categoryId)));
        Post post = this.postDtoToPost(postDto);
        post.setImageName("default.png");
        post.setUser(user);
        post.setCategory(category);
        return this.postToPostDto(postRepository.save(post));
    }

    @Override
    public PostDto updatePost(PostDto postDto, int postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post","id",String.valueOf(postId)));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());
        Post updatedPost = postRepository.save(post);
        return this.postToPostDto(updatedPost);
    }

    @Override
    public void deletePost(int postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post","id",String.valueOf(postId)));
        postRepository.delete(post);
    }

    @Override
    public PostDto getPost(int postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post","id",String.valueOf(postId)));
        return this.postToPostDto(post);
    }

    @Override
    public PostResponse getAllPosts(int pageNumber , int pageSize , String sortBy , String sortDirection) {
//        Pageable pageable = PageRequest.of(pageNumber,pageSize);
//        Page<Post> postPage = postRepository.findAll(pageable);
//        List<Post> posts = postPage.getContent();
//        return posts.stream().map(this::postToPostDto).collect(Collectors.toList());

        Sort sort = sortDirection.equalsIgnoreCase("asc") ? Sort.by(Sort.Direction.ASC,sortBy): Sort.by(Sort.Direction.DESC,sortBy);
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        Page<Post> postPage = postRepository.findAll(pageable);
        List<Post> posts = postPage.getContent();
        PostResponse postResponse = new PostResponse() ;
        postResponse.setContent(posts.stream().map(this::postToPostDto).collect(Collectors.toList()));
        postResponse.setPageNumber(postPage.getNumber()); // current page
        postResponse.setPageSize(postPage.getSize());
        postResponse.setTotalElement(postPage.getTotalElements());
        postResponse.setTotalPage(postPage.getTotalPages());
        postResponse.setLastPage(postPage.isLast());
        return postResponse;
    }

    @Override
    public List<PostDto> getPostsByCategory(int categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category","id",String.valueOf(categoryId)));
        List<Post> posts = postRepository.findAllByCategory(category);
        return posts.stream().map(this::postToPostDto).collect(Collectors.toList());
    }

    @Override
    public List<PostDto> getPostsByUser(int userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User","id",String.valueOf(userId)));
        List<Post> posts = postRepository.findAllByUser(user);
        return posts.stream().map(this::postToPostDto).collect(Collectors.toList());
    }

    @Override
    public List<PostDto> searchPosts(String keyWord) {
        //List<Post> posts = postRepository.findByTitleContaining(keyWord);
        List<Post> posts = postRepository.searchByTitle("%"+keyWord+"%");
        return posts.stream().map(this::postToPostDto).collect(Collectors.toList());
    }

    public Post postDtoToPost(PostDto postDto){
        return modelMapper.map(postDto,Post.class);
    }

    public PostDto postToPostDto(Post post){
        return modelMapper.map(post,PostDto.class);
    }

}
