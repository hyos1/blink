package com.example.blink.web.controller.api;

import com.example.blink.service.login.response.LoginMember;
import com.example.blink.service.member.MemberService;
import com.example.blink.service.member.response.MemberSimpleDto;
import com.example.blink.web.session.SessionConst;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
@RequiredArgsConstructor
public class RecommendationApiController {

    private final MemberService memberService;

    // 회원 추천
    @GetMapping("/members")
    public List<MemberSimpleDto> getRecommendations(
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER) LoginMember loginMember
    ) {
        return memberService.getRecommendedMembers(loginMember.getId());
    }
}
