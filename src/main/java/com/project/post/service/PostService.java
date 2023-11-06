package com.project.post.service;

import com.project.post.dto.PostRequestDto;
import com.project.post.dto.PostResponseDto;
import com.project.post.entity.Post;
import com.project.post.repository.PostRepository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

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

    public Long updatePost(Long id, PostRequestDto requestDto) {
        Post Post = postRepository.findById(id);
        if (Post != null) {
            // Post 내용 수정
            postRepository.update(id, requestDto);

            return id;
        } else {
            throw new IllegalArgumentException("선택한 게시글은 존재하지 않습니다.");
        }
    }

    public Long deletePost(Long id) {
        Post Post = postRepository.findById(id);
        if (Post != null) {
            // Post 삭제
            postRepository.delete(id);

            return id;
        } else {
            throw new IllegalArgumentException("선택한 게시글은 존재하지 않습니다.");
        }
    }

}
