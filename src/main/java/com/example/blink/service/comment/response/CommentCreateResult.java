package com.example.blink.service.comment.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CommentCreateResult {

    private Long commentId;
    private String authorName;
    private String authorProfileImage;
    private String content;
    private LocalDateTime createdAt;
}