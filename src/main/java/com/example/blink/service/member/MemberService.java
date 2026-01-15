package com.example.blink.service.member;

import com.example.blink.domain.Member;
import com.example.blink.repository.MemberRepository;
import com.example.blink.service.member.request.SignupCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    @Transactional
    public void save(SignupCommand signupCommand) {
        String encodedPassword = passwordEncoder.encode(signupCommand.getPassword());
        Member member = new Member(signupCommand.getName(), signupCommand.getEmail(), encodedPassword);
        memberRepository.save(member);
    }

    public MemberSidebarDto getMyProfile(Long loginMemberId) {
        return memberRepository.findSidebarInfoById(loginMemberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));
    }

}