package com.example.blink.domain;

import com.example.blink.exception.ClientException;
import com.example.blink.exhandler.ErrorCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.example.blink.exhandler.ErrorCode.*;

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

    private Comment(String content, Member writer) {
        this.content = content;
        writtenBy(writer);
    }

    public static Comment createComment(String content, Member writer) {
        validateContent(content);
        return new Comment(content, writer);
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
            throw new ClientException(COMMENT_CONTENT_REQUIRED); // 댓글 내용 필수
        }

        if (content.length() > 100) {
            throw new ClientException(COMMENT_CONTENT_TOO_LONG); // 댓글 100자 미만
        }
    }
}