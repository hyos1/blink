package com.example.blink.service.post.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class PostDetailDto {

    private Long authorId;
    private String authorName;
    private String authorProfileImage;

    private Long postId;
    private String content;
    private List<String> imageUrls;
    private LocalDateTime createdAt;

    private Long postLikeCount;
    private Long commentCount;

    private boolean isLikedByMe; // 내가 좋아요 눌렀는지

    private List<CommentDto> comments;

    @Getter
    @AllArgsConstructor
    public static class CommentDto {
        private Long authorId; // 댓글 작성자 ID
        private String authorName;
        private Long authorProfileImage;
        private String content;
        private LocalDateTime createdAt;
    }
}