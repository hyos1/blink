package com.example.blink.domain;

import com.example.blink.exception.ClientException;
import com.example.blink.file.request.UploadFile;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static com.example.blink.exhandler.ErrorCode.*;

@Entity
@Table(name = "posts")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    // 연관관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    // 게시물 이미지들 (게시물 삭제 시 이미지도 삭제)
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostImage> images = new ArrayList<>();

    // 게시물의 댓글들 (게시물 삭제 시 댓글도 삭제)
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    // 게시물의 좋아요들 (게시물 삭제 시 좋아요도 삭제)
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostLike> postLikes = new ArrayList<>();

    private Post(String content, Member member) {
        this.content = content;
        // Member - Post 편의 메서드
        writtenBy(member);
    }

    // 정적 팩토리 메서드
    public static Post createPost(String content, Member member, List<UploadFile> images) {

        //검증 로직
        validationContent(content);
        validationImage(images);

        Post post = new Post(content, member);

        // 이미지 추가
        int orderNum = 0;
        for (UploadFile image : images) {
            PostImage postImage = new PostImage(image.getImageUrl(), orderNum++);
            post.addImage(postImage);
        }
        return post;
    }

    // 편의 메서드

    private void writtenBy(Member member) {
        this.member = member;
        member.getPosts().add(this);
    }

    private void addImage(PostImage image) {
        images.add(image);
        image.attachTo(this);
    }

    public void addLike(PostLike like) {
        postLikes.add(like);
        like.attachTo(this);
    }

    public void addComment(Comment comment) {
        comments.add(comment);
        comment.attachTo(this);
    }

    // 검증 로직
    private static void validationContent(String content) {
        if (content == null || content.trim().isEmpty()) {
            throw new ClientException(POST_CONTENT_REQUIRED);
        }
        if (content.length() > 500) {
            throw new ClientException(POST_CONTENT_TOO_LONG); // 게시물 내용 500자까지 가능
        }
    }

    private static void validationImage(List<UploadFile> uploadFiles) {
        if (uploadFiles == null || uploadFiles.isEmpty()) {
            throw new ClientException(POST_IMAGE_REQUIRED); // 사진 최소 1장 이상
        } else {
            if (uploadFiles.size() > 3) {
                throw new ClientException(POST_IMAGE_LIMIT_EXCEEDED); // 사진 최대 3장까지 가능
            }
        }
    }
}