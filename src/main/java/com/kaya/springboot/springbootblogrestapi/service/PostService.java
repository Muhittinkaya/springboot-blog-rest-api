package com.kaya.springboot.springbootblogrestapi.service;

import com.kaya.springboot.springbootblogrestapi.dto.PostDto;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto);

    List<PostDto> getAllPosts();

    PostDto getPostById(long id);
}
