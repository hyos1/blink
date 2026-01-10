package com.example.blink.web.controller;

import com.example.blink.service.member.MemberService;
import com.example.blink.domain.Member;
import com.example.blink.web.dto.LoginForm;
import com.example.blink.web.session.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping
public class LoginController {

    private final MemberService memberService;

    @GetMapping("/login")
    public String login(@ModelAttribute("loginForm") LoginForm form) {
        return "login/loginForm";
    }

    @PostMapping("/login")
    public String loginFormV1(@Validated @ModelAttribute LoginForm form, BindingResult bindingResult,
                              HttpServletRequest request,
                              Model model) {

        //타입변환, 검증실패 시 로그인 폼 이동
        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "login/loginForm";
        }

        // 아이디
        Member loginMember = memberService.login(form.getEmail(), form.getPassword());
        if (loginMember == null) {
            bindingResult.reject("loginFail", "아이디 혹은 비밀번호가 일치하지 않습니다.");
            return "login/loginForm";
        }

        // 로그인 성공 후 로직
        HttpSession session = request.getSession(); //세션이 없으면 생성, 있으면 기존 세션 사용
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);

        model.addAttribute("member", loginMember);

        return "feed";
    }
    // MemberService login로직 Exception 반환으로 처리할 예정
}
