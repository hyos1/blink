package com.example.blink.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CommentResponseDto {

    private Long commentId;
    private String authorName;
    private String authorProfileImage;
    private String content;
    private LocalDateTime createdAt;
}