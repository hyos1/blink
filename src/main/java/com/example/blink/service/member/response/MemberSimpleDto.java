package com.example.blink.service.member.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

// 회원 검색, 친구 추천 용 응답 DTO
@Getter
@AllArgsConstructor
public class MemberSimpleDto {

    private Long memberId;
    private String name;
    private String profileImageUrl;
}