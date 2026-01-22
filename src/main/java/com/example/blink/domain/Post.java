package com.example.blink.domain;

import com.example.blink.file.request.UploadFile;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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

        Post post = new Post(content, member);
        int orderNum = 0;
        for (UploadFile image : images) {
            PostImage postImage = new PostImage(image.getImageUrl(), orderNum++);
            post.addImage(postImage);
        }
        return post;
    }

    // 편의 메서드

    public void writtenBy(Member member) {
        this.member = member;
        member.getPosts().add(this);
    }

    public void addImage(PostImage image) {
        images.add(image);
        image.attachTo(this);
    }

    public void addLike(PostLike like) {
        postLikes.add(like);
        like.attachTo(this);
    }
}