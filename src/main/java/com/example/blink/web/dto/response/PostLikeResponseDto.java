package com.example.blink.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostLikeResponseDto {

    private boolean likedByMe;
    private Long likeCount;
}