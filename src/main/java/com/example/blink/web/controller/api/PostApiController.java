package com.example.blink.web.controller.api;

import com.example.blink.service.comment.CommentService;
import com.example.blink.service.login.response.LoginMember;
import com.example.blink.service.post.PostService;
import com.example.blink.service.post.response.PostDetailDto;
import com.example.blink.web.dto.CommentRequestDto;
import com.example.blink.service.comment.response.CommentCreateResult;
import com.example.blink.service.post.response.PostLikeResultDto;
import com.example.blink.web.session.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostApiController {

    private final PostService postService;
    private final CommentService commentService;

    // 게시물 상세 조회
    @GetMapping("/api/posts/{postId}")
    public PostDetailDto getPostDetail(
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER) LoginMember loginMember,
            @PathVariable Long postId) {

        log.info("컨트롤러 실행");
        return postService.getPostDetail(postId, loginMember.getId());
    }

    // 게시물 좋아요
    @PostMapping("/api/posts/{postId}/like")
    public PostLikeResultDto toggleLike(
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER) LoginMember loginMember,
            @PathVariable Long postId) {

        return postService.toggleLike(loginMember.getId(), postId);
    }

    // 게시물에 댓글 추가
    @PostMapping("/api/posts/{postId}/comments")
    public CommentCreateResult addComment(
            @PathVariable Long postId,
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER) LoginMember loginMember,
            @RequestBody CommentRequestDto commentRequestDto) {
        // 댓글 dto에서도 검증할지 객체 내부 검증으로 끝낼지 고민(bindingResult)
        log.info("댓글 ={}", commentRequestDto.getContent());
        return commentService.addComment(postId, loginMember.getId(), commentRequestDto.getContent());
    }
}