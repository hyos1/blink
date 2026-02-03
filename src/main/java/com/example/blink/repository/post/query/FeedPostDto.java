package com.example.blink.repository.post.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FeedPostDto {

    private Long postId;
    private String content;
    private LocalDateTime createdAt;

    private Long authorId;
    private String authorName;
    private String authorProfileImage; // 작성자 프로필 사진

    private List<String> imageUrls; // 게시물 사진들

    private Long postLikeCount;
    private Long postCommentCount;
    private boolean likedByMe;

    public FeedPostDto(Long postId, String content, LocalDateTime createdAt, Long authorId, String authorName, String authorProfileImage, Long postLikeCount, Long postCommentCount, boolean likedByMe) {
        this.postId = postId;
        this.content = content;
        this.createdAt = createdAt;
        this.authorId = authorId;
        this.authorName = authorName;
        this.authorProfileImage = authorProfileImage;
        this.postLikeCount = postLikeCount;
        this.postCommentCount = postCommentCount;
        this.likedByMe = likedByMe;
    }
}