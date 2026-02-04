package com.example.blink.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "follows",
        uniqueConstraints = @UniqueConstraint(columnNames = {"follower_id", "following_id"}))
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Follow {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "follow_id")
    private Long id;

    // 연관관계

    // 팔로우 하는 사람 -> 철수
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follower_id")
    private Member follower;

    // 팔로우 당하는 사람 -> 영희
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "following_id")
    private Member following;

    // 편의 메서드

    // 팔로우 한 사람(ex 철수)
    private void followedBy(Member follower) {
        // 이 팔로우는 철수가 했다.
        this.follower = follower;
        // 철수의 Followings 리스트에 이 Follow 추가
        follower.getFollowings().add(this);
    }

    // 팔로우 받은 사람(ex 영희)
    private void follow(Member following) {
        // 팔로우 당한 사람은 영희
        this.following = following;
        // 영희의 followers 리스트에 이 Follow 추가
        following.getFollowers().add(this);
    }

    // 생성 메서드
    public static Follow createFollow(Member follower, Member following) {
        validateFollow(follower, following);

        Follow follow = new Follow();
        follow.followedBy(follower);
        follow.follow(following);

        return follow;
    }

    // 검증 메서드
    private static void validateFollow(Member follower, Member following) {
        if (follower.getId().equals(following.getId())) {
            throw new IllegalStateException("자기 자신을 팔로우할 수 없습니다.");
        }
    }

    @Override
    public String toString() {
        return "Follow{" +
                "id=" + id +
                ", follower=" + follower.getId() +
                ", following=" + following.getId() +
                '}';
    }
}