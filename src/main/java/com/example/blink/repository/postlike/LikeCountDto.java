package com.example.blink.repository.postlike;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LikeCountDto {

    private Long postId;
    private Long likeCount;
}