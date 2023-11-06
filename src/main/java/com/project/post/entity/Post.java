package com.project.post.entity;

import com.project.post.dto.PostRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class Post {

    private Long id;
    private String username;
    private String contents;
    private String title;
    private String password;
    private LocalDateTime createdAt;

    public Post(PostRequestDto requestDto){
        this.id = requestDto.getId();
        this.username = requestDto.getUsername();
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
        this.password = requestDto.getPassword();
        this.createdAt = requestDto.getCreatedAt();
    }
}
