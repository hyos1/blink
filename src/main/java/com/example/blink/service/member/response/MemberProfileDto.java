package com.example.blink.service.member.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MemberProfileDto {

    private Long id;
    private String name;
//    private String profileImageUrl; // 프로필 이미지
    private String bio; // 프로필 한 줄 소개

    private Long postCount;
    private Long followerCount;
    private Long followingCount;

    private boolean isMyProfile; // 내 프로필인지
    private boolean isFollowing; // 내가 팔로우 중인지

    public void setMyProfile(boolean myProfile) {
        isMyProfile = myProfile;
    }

    public void setFollowing(boolean following) {
        isFollowing = following;
    }

    // JPQL로 기본 정보만 받는 생성자
    public MemberProfileDto(Long id, String name, String bio, Long postCount, Long followerCount, Long followingCount) {
        this.id = id;
        this.name = name;
        this.bio = bio;
        this.postCount = postCount;
        this.followerCount = followerCount;
        this.followingCount = followingCount;
    }
}