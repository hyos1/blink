package com.example.blink.web.controller.api;

import com.example.blink.repository.post.query.FeedPostDto;
import com.example.blink.service.login.response.LoginMember;
import com.example.blink.service.post.PostService;
import com.example.blink.web.session.SessionConst;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequestMapping("/api/feed")
@RequiredArgsConstructor
public class FeedApiController {

    private final PostService postService;

    // 피드 화면 게시물 조회
    @GetMapping
    public String getFeedPosts(
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER) LoginMember loginMember,
            @RequestParam(defaultValue = "0") int page,
            Model model
    ) {
        // 게시물은 3개씩 페이징
        Page<FeedPostDto> posts = postService.getFeedPosts(loginMember.getId(), page, 3);

        model.addAttribute("posts", posts);

        // fragments/post-item.html의 post-list fragment 반환
        return "fragments/post-item :: post-list";
    }
}
