package com.project.post.repository;

import com.project.post.dto.PostRequestDto;
import com.project.post.dto.PostResponseDto;
import com.project.post.entity.Post;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PostRepository {
    private final JdbcTemplate jdbcTemplate;

    public PostRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Post save(Post post) {
        // DB 저장
        KeyHolder keyHolder = new GeneratedKeyHolder(); // 기본 키를 반환받기 위한 객체

        String sql = "INSERT INTO post (username, contents, title, password, createdAt) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(con -> {
                    PreparedStatement preparedStatement = con.prepareStatement(sql,
                            Statement.RETURN_GENERATED_KEYS);
                    //preparedStatement.setString(1, String.valueOf(post.getId()));
                    preparedStatement.setString(1, post.getUsername());
                    preparedStatement.setString(2, post.getContents());
                    preparedStatement.setString(3, post.getTitle());
                    preparedStatement.setString(4, post.getPassword());
                    //preparedStatement.setString(5, post.getCreatedAt());
                    preparedStatement.setString(5, String.valueOf(LocalDateTime.now()));

                    return preparedStatement;
                },
                keyHolder);
        // DB Insert 후 받아온 기본키 확인
        Long id = keyHolder.getKey().longValue();
        post.setId(id);

        return post;
    }

    public List<PostResponseDto> findAll() {
        // DB 조회
        String sql = "SELECT * FROM post";

        return jdbcTemplate.query(sql, new RowMapper<PostResponseDto>() {
            @Override
            public PostResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                // SQL 의 결과로 받아온 Post 데이터들을 PostResponseDto 타입으로 변환해줄 메서드
                Long id = rs.getLong("id");
                String username = rs.getString("username");
                String contents = rs.getString("contents");
                String title = rs.getString("title");
                String password = rs.getString("password");
                String createdAt2 = rs.getString("createdAt");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime createdAt = LocalDateTime.parse(createdAt2, formatter);
                return new PostResponseDto(id, username, contents, title, createdAt);
            }
        });
    }

    public void update(Long id, PostRequestDto requestDto) {
        String sql = "UPDATE post SET username = ?, contents = ?, title = ? WHERE id = ?";
        jdbcTemplate.update(sql, requestDto.getUsername(), requestDto.getContents(), requestDto.getTitle(), id);
    }

    public void delete(Long id) {
        String sql = "DELETE FROM post WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public Post findById(Long id) {
        // DB 조회
        String sql = "SELECT * FROM post WHERE id = ?";

        return jdbcTemplate.query(sql, resultSet -> {
            if (resultSet.next()) {
                Post post = new Post();
                post.setId(resultSet.getLong("id"));
                post.setUsername(resultSet.getString("username"));
                post.setContents(resultSet.getString("contents"));
                post.setPassword(resultSet.getString("password"));
                post.setTitle(resultSet.getString("title"));
                String temp = resultSet.getString("createdAt");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime createdAt = LocalDateTime.parse(temp, formatter);
                //post.setCreatedAt(resultSet.getString("createdAt"));
                post.setCreatedAt(createdAt);

                return post;
            } else {
                return null;
            }
        }, id);
    }
}
