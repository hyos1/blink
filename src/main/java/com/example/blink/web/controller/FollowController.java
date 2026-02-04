package com.example.blink.web.controller;

import com.example.blink.service.follow.FollowService;
import com.example.blink.service.login.response.LoginMember;
import com.example.blink.service.member.MemberService;
import com.example.blink.web.session.SessionConst;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequiredArgsConstructor
@RequestMapping("/follows")
public class FollowController {

    private final FollowService followService;
    private final MemberService memberService;

    // 이미 팔로우 중이면 언팔로우, 아니면 팔로우 생성
    @PostMapping("/{followingId}")
    public String followUser(@SessionAttribute(name = SessionConst.LOGIN_MEMBER) LoginMember loginMember,
                             @PathVariable("followingId") Long followingId) {

        followService.toggleFollow(loginMember.getId(), followingId);

        String targetName = memberService.getMemberNameById(followingId);
        return "redirect:/members/profile/" + targetName;
    }
}