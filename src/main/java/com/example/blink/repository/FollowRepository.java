package com.example.blink.repository;

import com.example.blink.domain.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    // 팔로워 수 조회
    Long countByFollowingId(Long followingId);

    // 팔로잉 수 조회
    Long countByFollowerId(Long followerId);

    // 팔로잉 여부 확인
    boolean existsByFollowerIdAndFollowingId(Long followerId, Long followingId);

    // 특정 팔로우 관계 조회
    Optional<Follow> findByFollowerIdAndFollowingId(Long followerId, Long followingId);

    // 특정 팔로우 삭제
    void deleteByFollowerIdAndFollowingId(Long followerId, Long followingId);
}