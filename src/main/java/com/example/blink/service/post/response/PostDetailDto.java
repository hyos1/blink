package com.example.blink.service.post.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class PostDetailDto {

    // 작성자 정보
    private Long authorId;
    private String authorName;
    private String authorProfileImage;

    // 게시물 정보
    private Long postId;
    private String content;
    private List<String> imageUrls;
    private LocalDateTime createdAt;

    private Long postLikeCount;
    private Long commentCount;

    private boolean isLikedByMe; // 내가 좋아요 눌렀는지
    private boolean isMyPost; // 내 게시물인지

    private List<CommentDto> comments;

    @Getter
    @AllArgsConstructor
    public static class CommentDto {
        private Long commentId;
        private String authorName;
        private String authorProfileImage;
        private String content;
        private LocalDateTime createdAt;
        private Boolean isMyComment;
    }
}