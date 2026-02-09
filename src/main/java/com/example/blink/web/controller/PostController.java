package com.example.blink.web.controller;

import com.example.blink.exception.ClientException;
import com.example.blink.file.FileStore;
import com.example.blink.file.request.UploadFile;
import com.example.blink.service.login.response.LoginMember;
import com.example.blink.service.post.PostService;
import com.example.blink.service.post.request.CreatePostCommand;
import com.example.blink.web.dto.CreatePostForm;
import com.example.blink.web.session.SessionConst;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final FileStore fileStore;

    // 게시물 작성 페이지
    @GetMapping("/add")
    public String addForm(@ModelAttribute CreatePostForm form) {
        return "posts/addPost";
    }

    @PostMapping("/add")
    public String add(@SessionAttribute(name = SessionConst.LOGIN_MEMBER) LoginMember loginMember,
                      @ModelAttribute CreatePostForm form,
                      BindingResult bindingResult) {

        // 태그 자체가 없거나, 태그 있는데 사진 안 넘긴 경우
        if (form.getImageFiles() == null || form.getImageFiles().isEmpty()
                || form.getImageFiles().get(0).isEmpty()) {
            bindingResult.reject("imageRequired", "사진은 최소 1장 이상 필요합니다.");
            return "posts/addPost";
        }

        try {
            // 파일 저장
            List<UploadFile> images = fileStore.storeFiles(form.getImageFiles());

            CreatePostCommand command = new CreatePostCommand(
                    loginMember.getId(), form.getContent(), images
            );

            // 게시물 생성
            postService.addPost(command);
        } catch (ClientException e) {
            // 요청한 값 검증 오류
            bindingResult.reject("postCreateFail", e.getMessage());
            return "posts/addPost";
        }

        return "redirect:/members/profile/me";
    }
}