package com.kaya.springboot.springbootblogrestapi.service;

import com.kaya.springboot.springbootblogrestapi.dto.PostDto;
import com.kaya.springboot.springbootblogrestapi.dto.PostResponse;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto);

    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy);

    PostDto getPostById(long id);

    PostDto updatePost(PostDto postDto, long id);

    void deletePostById(long id);
}
