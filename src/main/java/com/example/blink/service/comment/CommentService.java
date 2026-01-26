package com.example.blink.service.comment;

import com.example.blink.domain.Comment;
import com.example.blink.domain.Member;
import com.example.blink.domain.Post;
import com.example.blink.repository.CommentRepository;
import com.example.blink.repository.MemberRepository;
import com.example.blink.repository.PostRepository;
import com.example.blink.web.dto.response.CommentResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    // 댓글 작성
    @Transactional
    public CommentResponseDto addComment(Long postId, Long memberId, String content) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 게시물입니다."));
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 회원입니다.")
        );

        Comment comment = Comment.createComment(content, post, member);
        commentRepository.save(comment);

        return new CommentResponseDto(
                comment.getId(), member.getName(), member.getProfileImage(),
                comment.getContent(), comment.getCreatedAt());
    }
}