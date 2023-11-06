package com.project.post.dto;

import com.project.post.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostResponseDto {
    private Long id;
    private String username;
    private String contents;
    private String title;
    private String password;
    private LocalDateTime createdAt;

    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.username = post.getUsername();
        this.contents = post.getContents();
        this.title = post.getTitle();
        this.createdAt = post.getCreatedAt();
    }

    public PostResponseDto(Long id, String username, String contents, String title, LocalDateTime createdAt) {
        this.id = id;
        this.username = username;
        this.contents = contents;
        this.title = title;
        this.createdAt = createdAt;
    }
}
