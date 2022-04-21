package com.kaya.springboot.springbootblogrestapi.repository;

import com.kaya.springboot.springbootblogrestapi.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
