package com.example.blink.service.login;

import com.example.blink.domain.Member;
import com.example.blink.exception.ClientException;
import com.example.blink.exhandler.ErrorCode;
import com.example.blink.repository.member.MemberRepository;
import com.example.blink.service.login.request.LoginCommand;
import com.example.blink.service.login.response.LoginMember;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.blink.exhandler.ErrorCode.*;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LoginService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    @Transactional
    public LoginMember login(LoginCommand loginCommand) {

        // 컨트롤러에서 BindingResult로 처리하기 위해 null 반환
        Member member = memberRepository.findByEmail(loginCommand.getEmail())
                .orElse(null);

        if (member == null) {
            return null;
        }

        // 입력한 비밀번호와 찾은 회원의 비밀번호가 같아야함
        if (!passwordEncoder.matches(loginCommand.getPassword(), member.getPassword())) {
            log.info("비밀번호가 일치하지 않습니다.");
            return null;
        }

        return new LoginMember(member.getId(), member.getName());
    }
}