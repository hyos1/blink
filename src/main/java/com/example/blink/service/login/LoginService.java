package com.example.blink.service.login;

import com.example.blink.domain.Member;
import com.example.blink.repository.member.MemberRepository;
import com.example.blink.service.login.request.LoginCommand;
import com.example.blink.service.login.response.LoginMember;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LoginService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    @Transactional
    public LoginMember login(LoginCommand loginCommand) {

        Member member = memberRepository.findByEmail(loginCommand.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다,"));

        if (!passwordEncoder.matches(loginCommand.getPassword(), member.getPassword())) {
            log.info("비밀번호 오류");
            throw new IllegalArgumentException("비밀번호가 맞지 않습니다.");
        }

        return new LoginMember(member.getId(), member.getName());
    }
}