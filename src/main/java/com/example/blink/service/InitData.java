package com.example.blink.service;

import com.example.blink.domain.Comment;
import com.example.blink.domain.Member;
import com.example.blink.domain.Post;
import com.example.blink.file.request.UploadFile;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class InitData implements ApplicationRunner {

    private final EntityManager em;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        for (int i = 1; i <= 100; i++) {
            String encodedPassword = passwordEncoder.encode("1234");
            Member member = new Member("user" + i, "user" + i + "@test.com", encodedPassword);
            em.persist(member);

            for (int p = 1; p <= 10; p++) {
                List<UploadFile> images = List.of(
                        new UploadFile("dummy1.jpg", "/postImages/dummy1.jpg"),
                        new UploadFile("dummy2.jpg", "/postImages/dummy2.jpg"),
                        new UploadFile("dummy3.jpg", "/postImages/dummy3.jpg")
                );
                Post post = Post.createPost(member.getName() + "의 게시물", member, images);
                em.persist(post);

                for (int c = 0; c < 3; c++) {
                    Comment.createComment("댓글" + c, member);
                }
            }
        }
    }
//
//    @PostConstruct
//    public void init() {
//        initData();
//    }
//
//    @Transactional
//    public void initData() {
//        for (int i = 0; i < 100; i++) {
//            String encodedPassword = passwordEncoder.encode("1234");
//            Member member = new Member("user" + i, "user" + i + "@test.com", encodedPassword);
//            em.persist(member);
//
//            for (int p = 1; p <= 10; p++) {
//                List<UploadFile> images = List.of(
//                        new UploadFile("dummy1.jpg", "/postImages/dummy1.jpg"),
//                        new UploadFile("dummy2.jpg", "/postImages/dummy2.jpg"),
//                        new UploadFile("dummy3.jpg", "/postImages/dummy3.jpg")
//                );
//                Post post = Post.createPost(member.getName() + "의 게시물", member, images);
//                em.persist(post);
//
//                for (int c = 0; c < 3; c++) {
//                    Comment.createComment("댓글" + c, member);
//                }
//            }
//        }
//    }
}