package com.example.blink.web.controller.api;

import com.example.blink.service.member.MemberService;
import com.example.blink.service.member.response.MemberSimpleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class SearchApiController {

    private final MemberService memberService;

    // 회원 이름으로 검색
    @GetMapping("/members")
    public List<MemberSimpleDto> searchMembers(@RequestParam String query) {

        if (query == null || query.trim().isEmpty()) {
            return List.of();
        }
        return memberService.searchMembers(query);
    }
}