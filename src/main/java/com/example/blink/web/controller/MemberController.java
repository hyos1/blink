package com.example.blink.web.controller;

import com.example.blink.service.login.response.LoginMember;
import com.example.blink.service.member.MemberService;
import com.example.blink.service.member.request.SignupCommand;
import com.example.blink.service.member.response.MemberProfileDto;
import com.example.blink.service.post.PostService;
import com.example.blink.service.post.response.ProfilePostDto;
import com.example.blink.web.dto.SignupForm;
import com.example.blink.web.session.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final PostService postService;

    @GetMapping("/add")
    public String addForm(@ModelAttribute SignupForm form) {
        return "members/addMemberForm";
    }

    @PostMapping("/add")
    public String save(@Validated @ModelAttribute SignupForm form, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "members/addMemberForm";
        }

        SignupCommand signupCommand = new SignupCommand(form.getName(), form.getEmail(), form.getPassword());

        memberService.save(signupCommand);
        return "redirect:/login";
    }

    // 내 프로필 보기 (홈 버튼 클릭 시)
    @GetMapping("/profile/me")
    public String myProfile(@SessionAttribute(name = SessionConst.LOGIN_MEMBER) LoginMember loginMember, Model model) {

        // 내 프로필 조회 (memberId = loginMemberId)
        MemberProfileDto profile = memberService.getProfile(loginMember.getId(), loginMember.getId());
        model.addAttribute("profile", profile);

        // 프로필 게시물 목록 조회
        List<ProfilePostDto> posts = postService.getPostsByMemberId(loginMember.getId());
        model.addAttribute("posts", posts);
        return "members/memberProfile";
    }
}