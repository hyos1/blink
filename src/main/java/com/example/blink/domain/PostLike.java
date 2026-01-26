package com.example.blink.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "post_likes")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostLike extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_like_id")
    private Long id;

    // 연관관계

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    private PostLike(Post post, Member member) {
        attachTo(post);
        likedBy(member);
    }

    public static PostLike createPostLike(Post post, Member member) {
        return new PostLike(post, member);
    }

    // 편의 메서드
    public void attachTo(Post post) {
        this.post = post;
    }

    public void likedBy(Member member) {
        this.member = member;
    }
}