package com.example.blink.web.controller.view;

import com.example.blink.exception.ClientException;
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

    // 회원가입 페이지 이동
    @GetMapping("/add")
    public String addForm(@ModelAttribute SignupForm form) {
        return "members/addMemberForm";
    }

    // 회원가입 처리
    @PostMapping("/add")
    public String save(@Validated @ModelAttribute SignupForm form, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "members/addMemberForm";
        }

        try {
            SignupCommand signupCommand = new SignupCommand(form.getName(), form.getEmail(), form.getPassword());
            memberService.save(signupCommand);
            return "redirect:/login";
        } catch (ClientException e) {
            // 중복 이름, 이메일 오류
            bindingResult.reject("signupFail", e.getMessage());
            return "members/addMemberForm";
        }
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

    // 회원 프로필 페이지
    @GetMapping("/profile/{name}")
    public String profile(@SessionAttribute(name = SessionConst.LOGIN_MEMBER) LoginMember loginMember,
                          @PathVariable("name") String name, Model model) {

        // username으로 memberId 조회
        Long targetMemberId = memberService.getMemberIdByName(name);

        // 프로필 조회
        MemberProfileDto profile = memberService.getProfile(targetMemberId, loginMember.getId());
        model.addAttribute("profile", profile);

        // 프로필 게시물 목록 조회
        List<ProfilePostDto> posts = postService.getPostsByMemberId(targetMemberId);
        model.addAttribute("posts", posts);

        return "members/memberProfile";
    }
}