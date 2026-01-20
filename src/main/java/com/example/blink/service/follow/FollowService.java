package com.example.blink.service.follow;

import com.example.blink.domain.Follow;
import com.example.blink.domain.Member;
import com.example.blink.repository.FollowRepository;
import com.example.blink.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void toggleFollow(Long followerId, Long followingId) {

        boolean isFollowing = followRepository.existsByFollowerIdAndFollowingId(followerId, followingId);

        // 이미 팔로우 중이면 -> 언팔로우
        if (isFollowing) {
            followRepository.deleteByFollowerIdAndFollowingId(followerId, followingId);
            log.info("언팔로우 성공: followerId={}, followingId={}", followerId, followingId);
            return;
        }

        // 팔로우
        Member follower = memberRepository.findById(followerId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        Member following = memberRepository.findById(followingId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        // 생성 메서드에서 자기 자신 팔로우 여부 검증
        Follow follow = Follow.createFollow(follower, following);
        followRepository.save(follow);

        log.info("팔로우 성공: followerId={}, followingId={}", followerId, followingId);
    }
}