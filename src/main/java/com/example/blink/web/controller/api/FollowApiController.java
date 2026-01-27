package com.example.blink.web.controller.api;

import com.example.blink.service.follow.FollowService;
import com.example.blink.service.follow.response.FollowDto;
import com.example.blink.service.login.response.LoginMember;
import com.example.blink.web.session.SessionConst;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/follows")
public class FollowApiController {

    private final FollowService followService;

    @GetMapping("/{memberId}/followers")
    public List<FollowDto> getFollowers(
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER) LoginMember loginMember,
            @PathVariable Long memberId) {
        return followService.getFollowers(memberId, loginMember.getId());
    }
}