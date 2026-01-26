package com.example.blink.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "comments")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private Comment(String content, Post post, Member writer) {
        this.content = content;
        attachTo(post);
        writtenBy(writer);
    }

    public static Comment createComment(String content, Post post, Member writer) {
        validateContent(content);
        return new Comment(content, post, writer);
    }

    // 연관관계

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;


    // 편의 메서드

    public void attachTo(Post post) {
        this.post = post;
    }

    public void writtenBy(Member writer) {
        this.member = writer;
    }

    // 검증 로직
    private static void validateContent(String content) {
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("댓글 내용은 필수입니다.");
        }

        if (content.length() > 100) {
            throw new IllegalArgumentException("댓글은 100자를 초과할 수 없습니다.");
        }
    }
}