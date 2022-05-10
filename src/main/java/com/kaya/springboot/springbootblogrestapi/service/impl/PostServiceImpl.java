package com.kaya.springboot.springbootblogrestapi.service.impl;

import com.kaya.springboot.springbootblogrestapi.dto.PostDto;
import com.kaya.springboot.springbootblogrestapi.dto.PostResponse;
import com.kaya.springboot.springbootblogrestapi.entity.Post;
import com.kaya.springboot.springbootblogrestapi.exception.ResourceNotFoundException;
import com.kaya.springboot.springbootblogrestapi.repository.PostRepository;
import com.kaya.springboot.springbootblogrestapi.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        //Convert Dto to Entity
        Post post = convertDtoToEntity(postDto);
        Post newPost = postRepository.save(post);

        //convert Entity to dto
        PostDto postResponse = convertEntityToDto(newPost);

        return postResponse;
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy) {

        //  Create pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        Page<Post> posts = postRepository.findAll(pageable);

        // get content
        List<Post> listOfPost = posts.getContent();

        List<PostDto> content = listOfPost.stream().map(post -> convertEntityToDto(post)).collect(Collectors.toList()); // Lambda expression, operating on lists

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setLast(posts.isLast());
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());

        return postResponse;
    }

    @Override
    public PostDto getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        return convertEntityToDto(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, long id) {
        // 1- At first get the post with related id
        // 2- then update it
        // 3- then save the updated version to database
        // 4- then return

        // 1
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

        //2
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setDescription(post.getDescription());

        //3
        Post updatedPost = postRepository.save(post);

        //4
        return convertEntityToDto(post);
    }

    @Override
    public void deletePostById(long id) {
        // 1- At first get the post with related id
        // 2- then delete it with the help of repository

        //1
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        postRepository.delete(post);
    }

    //convert Entity to dto method
    private PostDto convertEntityToDto(Post post){
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setContent(post.getContent());
        postDto.setDescription(post.getDescription());

        return postDto;
    }

    //Convert Dto to Entity
    private Post convertDtoToEntity(PostDto postDto){
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        return post;
    }
}
