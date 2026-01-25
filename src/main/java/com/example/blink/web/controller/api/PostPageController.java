package com.example.blink.web.controller.api;

import com.example.blink.service.login.response.LoginMember;
import com.example.blink.service.post.PostService;
import com.example.blink.service.post.response.PostDetailDto;
import com.example.blink.web.session.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostPageController {

    private final PostService postService;

    // 게시물 상세 조회
    @GetMapping("/api/posts/{postId}")
    public PostDetailDto getPostDetail(
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER) LoginMember loginMember,
            @PathVariable Long postId) {

        log.info("컨트롤러 실행");
        return postService.getPostDetail(postId, loginMember.getId());
    }
}