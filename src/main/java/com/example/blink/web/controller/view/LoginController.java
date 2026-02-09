package com.example.blink.web.controller.view;

import com.example.blink.service.login.LoginService;
import com.example.blink.service.login.request.LoginCommand;
import com.example.blink.service.login.response.LoginMember;
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

    private final LoginService loginService;

    @GetMapping("/login")
    public String login(@ModelAttribute("loginForm") LoginForm form) {
        return "login/loginForm";
    }

    @PostMapping("/login")
    public String loginFormV1(@Validated @ModelAttribute LoginForm form, BindingResult bindingResult,
                              @RequestParam(defaultValue = "/") String redirectURL,
                              HttpServletRequest request,
                              Model model) {

        //타입변환, 검증실패 시 로그인 폼 이동
        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "login/loginForm";
        }

        // 아이디
        LoginCommand loginCommand = new LoginCommand(form.getEmail(), form.getPassword());
        // 로그인 후 세션에 넣을 값(id, name)
        LoginMember loginMember = loginService.login(loginCommand);
        log.info("로그인 통과");
        if (loginMember == null) {
            bindingResult.reject("loginFail", "아이디 혹은 비밀번호가 일치하지 않습니다.");
            return "login/loginForm";
        }

        // 로그인 성공 후 로직
        HttpSession session = request.getSession(); //세션이 없으면 생성, 있으면 기존 세션 사용
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);

        // 로그인 성공 시 기존 요청했던 URL로 이동
        return "redirect:" + redirectURL;
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {

        HttpSession session = request.getSession(false); // 세션 새로 생성하지 않음
        if (session != null) {
            session.invalidate(); //세션 안에 있는 모든 데이터 삭제
        }
        return "redirect:/";
    }
}