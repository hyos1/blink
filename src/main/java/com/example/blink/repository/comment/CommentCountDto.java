package com.example.blink.repository.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentCountDto {

    private Long postId;
    private Long commentCount;
}