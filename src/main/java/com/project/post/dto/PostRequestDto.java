package com.project.post.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostRequestDto {
    private Long id;
    private String username;
    private String contents;
    private String title;
    private String password;
    private LocalDateTime createdAt;
}