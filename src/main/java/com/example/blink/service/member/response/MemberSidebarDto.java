package com.example.blink.service.member.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class MemberSidebarDto {

    private Long id;
    private String name;
    private String email;
    private String profileImage;
    private Long postCount;
    private Long followerCount;
    private Long followingCount;
}