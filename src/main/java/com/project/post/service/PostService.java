package com.project.post.service;

import com.project.post.dto.PostRequestDto;
import com.project.post.dto.PostResponseDto;
import com.project.post.entity.Post;
import com.project.post.repository.PostRepository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Objects;

public class PostService {
    private final PostRepository postRepository;


    public PostService(JdbcTemplate jdbcTemplate) {
        this.postRepository = new PostRepository(jdbcTemplate);
    }

    public PostResponseDto createPost(PostRequestDto requestDto) {
        // RequestDto -> Entity
        Post post = new Post(requestDto);

        // DB 저장
        Post savePost = postRepository.save(post);

        // Entity -> ResponseDto
        PostResponseDto postResponseDto = new PostResponseDto(savePost);

        return postResponseDto;
    }

    public List<PostResponseDto> getPosts() {
        // DB 조회
        return postRepository.findAll();
    }


    public PostResponseDto getPost(Long id){
        Post post = postRepository.findById(id);
        return new PostResponseDto(post);
    }

    public PostResponseDto updatePost(Long id, PostRequestDto requestDto) {
        Post post = postRepository.findById(id);
       //password = password.replaceAll("\"", "");

        if(post == null){
            throw new IllegalArgumentException("선택한 게시글은 존재하지 않습니다.");
        }
        if (!Objects.equals(post.getPassword(), requestDto.getPassword())){
            throw new IllegalArgumentException("비밀번호가 틀렸습니다.");
       } else {
            postRepository.update(id, requestDto);
            return new PostResponseDto(post);
        }
    }

    public Long deletePost(Long id, String password) {
        Post post = postRepository.findById(id);
        password = password.replaceAll("\"", "");
        if(post ==null){
            throw new IllegalArgumentException("선택한 게시글은 존재하지 않습니다.");
        }
        if (!Objects.equals(post.getPassword(), password)){
            throw new IllegalArgumentException("비밀번호가 틀렸습니다.");
        } else {
            // Post 삭제
            postRepository.delete(id);

            return id;
        }
    }

}
