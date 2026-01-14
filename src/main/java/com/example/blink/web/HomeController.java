package com.example.blink.web;

import com.example.blink.domain.Member;
import com.example.blink.service.member.MemberService;
import com.example.blink.web.dto.LoginForm;
import com.example.blink.web.session.SessionConst;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberService memberService;

    //사용자의 JSESSIONID 속성 이름 중 LOGIN_MEMBER 있으면 value할당, 없으면 null
    @GetMapping("/")
    public String homeV1(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember,
                         @ModelAttribute("loginForm") LoginForm form, Model model) {

        // 로그인 안 돼있으면 로그인 페이지로 이동
        if (loginMember == null) {
            return "login/loginForm";
        }

        model.addAttribute("member", loginMember);
        return "feed";
    }
}