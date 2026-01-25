package com.example.blink.service;

import com.example.blink.service.member.MemberService;
import com.example.blink.service.member.request.SignupCommand;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@RequiredArgsConstructor
public class InitMember {

    private final MemberService memberService;

    @PostConstruct
    public void init() {
        SignupCommand signupCommand1 = new SignupCommand("111", "111@111.com", "111");
        SignupCommand signupCommand2 = new SignupCommand("222", "222@222.com", "222");
        memberService.save(signupCommand1);
        memberService.save(signupCommand2);
    }
}