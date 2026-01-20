package com.example.blink.service.member;

import com.example.blink.domain.Member;
import com.example.blink.repository.FollowRepository;
import com.example.blink.repository.MemberRepository;
import com.example.blink.repository.PostRepository;
import com.example.blink.service.member.request.SignupCommand;
import com.example.blink.service.member.response.MemberProfileDto;
import com.example.blink.service.member.response.MemberSidebarDto;
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
    private final PostRepository postRepository;
    private final FollowRepository followRepository;

    // 이름으로 memberId 조회
    public Long getMemberIdByName(String name) {
        return memberRepository.findIdByName(name).orElseThrow(
                () -> new IllegalArgumentException("해당 회원이 존재하지 않습니다.")
        );
    }

    public String getMemberNameById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."))
                .getName();
    }

    @Transactional
    public void save(SignupCommand signupCommand) {

        if (memberRepository.existsByName(signupCommand.getName())) {
            throw new IllegalStateException("사용 중인 이름입니다.");
        }

        if (memberRepository.existsByEmail(signupCommand.getEmail())) {
            throw new IllegalStateException("사용 중인 이메일입니다.");
        }

        String encodedPassword = passwordEncoder.encode(signupCommand.getPassword());
        Member member = new Member(signupCommand.getName(), signupCommand.getEmail(), encodedPassword);
        memberRepository.save(member);
    }

    // 피드 화면 사이드에 회원 정보 조회
    public MemberSidebarDto getMemberSidebarDto(Long loginMemberId) {
        return memberRepository.findSidebarInfoById(loginMemberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));
    }

    // 프로필 정보 조회
    public MemberProfileDto getProfile(Long targetMemberId, Long loginMember) {

        // 기본 정보 조회
        MemberProfileDto profile = memberRepository.findProfileById(targetMemberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));

        // 게시물 수, 팔로잉 팔로워 수 설정
        profile.setPostCount(postRepository.countPostsByMemberId(targetMemberId));
        profile.setFollowerCount(followRepository.countByFollowingId(targetMemberId));
        profile.setFollowingCount(followRepository.countByFollowerId(targetMemberId));

        // 요청한 사람과 해당 회원 ID가 같은 경우 = 내 프로필
        profile.setMyProfile(targetMemberId.equals(loginMember));
        // 내 프로필이 아닌 경우
        if (!profile.isMyProfile()) {
            // 요청한 사람이 해당 회원을 팔로우 중인지 확인
            profile.setFollowing(followRepository.existsByFollowerIdAndFollowingId(loginMember, targetMemberId));
        } else {
            profile.setFollowing(false);
        }

        return profile;
    }
}