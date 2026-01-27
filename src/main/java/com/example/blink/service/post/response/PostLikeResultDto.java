package com.example.blink.service.post.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostLikeResultDto {

    private boolean likedByMe;
    private Long likeCount;
}