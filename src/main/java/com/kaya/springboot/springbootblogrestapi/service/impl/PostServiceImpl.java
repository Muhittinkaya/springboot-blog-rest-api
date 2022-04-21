package com.kaya.springboot.springbootblogrestapi.service.impl;

import com.kaya.springboot.springbootblogrestapi.dto.PostDto;
import com.kaya.springboot.springbootblogrestapi.entity.Post;
import com.kaya.springboot.springbootblogrestapi.repository.PostRepository;
import com.kaya.springboot.springbootblogrestapi.service.PostService;
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
    public List<PostDto> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream().map(post -> convertEntityToDto(post)).collect(Collectors.toList()); // Lambda expression, operating on lists
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
