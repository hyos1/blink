package com.example.blink.repository.follow;

import com.example.blink.domain.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
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

    // 특정 회원을 팔로우 하는 사람들 조회
    @Query("select f from Follow f " +
            "join fetch f.follower " +
            "where f.following.id = :memberId")
    List<Follow> findByFollowingId(@Param("memberId") Long memberId);

    // 특정 회원이 팔로우 한 사람들 조회
    @Query("select f from Follow f " +
            "join fetch f.following " +
            "where f.follower.id = :memberId")
    List<Follow> findByFollowerId(@Param("memberId") Long memberId);

    // 로그인 한 사람이 팔로우 한 사람들 id
    @Query("select f.following.id " +
            "from Follow f " +
            "where f.follower.id = :loginMemberId")
    List<Long> findFollowingIdsByFollowerId(@Param("loginMemberId") Long loginMemberId);
}