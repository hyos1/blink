package com.example.blink.service.follow.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FollowDto {

    private Long memberId;
    private String name;
    private String profileImageUrl;
    private boolean followByMe; // 나한테 팔로우 당했는지
}