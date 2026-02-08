package com.example.blink.service.comment;

import com.example.blink.domain.Comment;
import com.example.blink.domain.Member;
import com.example.blink.domain.Post;
import com.example.blink.exception.ClientException;
import com.example.blink.exhandler.ErrorCode;
import com.example.blink.repository.comment.CommentRepository;
import com.example.blink.repository.member.MemberRepository;
import com.example.blink.repository.post.PostRepository;
import com.example.blink.service.comment.response.CommentCreateResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.blink.exhandler.ErrorCode.*;

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
                () -> new ClientException(POST_NOT_FOUND));
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new ClientException(USER_NOT_FOUND)
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
                () -> new ClientException(COMMENT_NOT_FOUND)
        );

        if (!comment.getMember().getId().equals(loginMemberId)) {
            throw new ClientException(COMMENT_DELETE_FORBIDDEN);
        }

        commentRepository.delete(comment);
    }
}