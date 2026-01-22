package com.example.blink.service.post;

import com.example.blink.domain.Member;
import com.example.blink.domain.Post;
import com.example.blink.domain.PostImage;
import com.example.blink.file.request.UploadFile;
import com.example.blink.repository.MemberRepository;
import com.example.blink.repository.PostRepository;
import com.example.blink.service.post.request.CreatePostCommand;
import com.example.blink.service.post.response.ProfilePostDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long addPost(CreatePostCommand command) {
        Member member = memberRepository.findById(command.getMemberId()).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 회원입니다.")
        );

        // 이미지 추가
        List<UploadFile> images = command.getImages();
        Post post = Post.createPost(command.getContent(), member, images);

        postRepository.save(post);
        return post.getId();
    }

    // 프로필 화면용 게시물 목록 조회
    public List<ProfilePostDto> getPostsByMemberId(Long memberId) {
        return postRepository.findProfilePostsByMemberId(memberId);
    }
}