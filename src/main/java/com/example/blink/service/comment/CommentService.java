package com.example.blink.service.comment;

import com.example.blink.domain.Comment;
import com.example.blink.domain.Member;
import com.example.blink.domain.Post;
import com.example.blink.repository.comment.CommentRepository;
import com.example.blink.repository.member.MemberRepository;
import com.example.blink.repository.post.PostRepository;
import com.example.blink.service.comment.response.CommentCreateResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;

    // 댓글 작성
    @Transactional
    public CommentCreateResult addComment(Long postId, Long memberId, String content) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 게시물입니다."));
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 회원입니다.")
        );

        Comment comment = Comment.createComment(content, member);
        // Post CascadeType 의하여 Comment 저장됨
        post.addComment(comment);

        return new CommentCreateResult(
                comment.getId(), member.getName(), member.getProfileImage(),
                comment.getContent(), comment.getCreatedAt(), true);
    }

    // 댓글 삭제
    @Transactional
    public void deleteComment(Long commentId, Long loginMemberId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 댓글입니다.")
        );

        if (!comment.getMember().getId().equals(loginMemberId)) {
            throw new IllegalArgumentException("본인 댓글만 삭제할 수 있습니다.");
        }

        commentRepository.delete(comment);
    }
}