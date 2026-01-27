package com.example.blink.service.follow;

import com.example.blink.domain.Follow;
import com.example.blink.domain.Member;
import com.example.blink.repository.FollowRepository;
import com.example.blink.repository.MemberRepository;
import com.example.blink.service.follow.response.FollowDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final MemberRepository memberRepository;

    // 이미 팔로우 중이면 언팔로우, 아니면 팔로우 생성
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

    // 특정 회원 "팔로워" 조회
    public List<FollowDto> getFollowers(Long memberId, Long loginMemberId) {

        // 특정 회원 팔로워들
        List<Follow> follows = followRepository.findByFollowingId(memberId);

        // 로그인 한 사람이 팔로우 한 사람들 id
        Set<Long> myFollowingIds = followRepository
                .findFollowingIdsByFollowerId(loginMemberId)
                .stream()
                .collect(Collectors.toSet());

        return follows.stream()
                .map(follow -> {
                    // memberId를 팔로우 하는 사람들
                    Member follower = follow.getFollower();
                    // 해당 팔로워들을 로그인한 사람이 팔로우 했는지 확인
                    boolean followByMe = myFollowingIds.contains(follower.getId());
                    return new FollowDto(
                            follower.getId(), follower.getName(),
                            follower.getProfileImage(), followByMe);
                }).collect(Collectors.toList());
    }
}