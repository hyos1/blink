package com.example.blink.service.post.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ProfilePostDto {

    private Long id;
    private String imageUrl; // 첫 번째 사진(없으면 null)
    private Long likeCount;
    private Long commentCount;
    private LocalDateTime createdAt;
}