package com.example.blink.service.post;

import com.example.blink.domain.*;
import com.example.blink.file.request.UploadFile;
import com.example.blink.repository.CommentRepository;
import com.example.blink.repository.MemberRepository;
import com.example.blink.repository.PostLikeRepository;
import com.example.blink.repository.PostRepository;
import com.example.blink.service.post.request.CreatePostCommand;
import com.example.blink.service.post.response.PostDetailDto;
import com.example.blink.service.post.response.ProfilePostDto;
import com.example.blink.service.post.response.PostLikeResultDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final PostLikeRepository postLikeRepository;
    private final CommentRepository commentRepository;

    // 게시물 생성
    @Transactional
    public Long addPost(CreatePostCommand command) {
        Member member = memberRepository.findById(command.getMemberId()).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 회원입니다.")
        );

        // 이미지 추가
        List<UploadFile> images = command.getImages();
        Post post = Post.createPost(command.getContent(), member, images); //객체 내부에서 검증

        postRepository.save(post);
        return post.getId();
    }

    // 프로필 화면용 게시물 목록 조회
    public List<ProfilePostDto> getPostsByMemberId(Long memberId) {
        return postRepository.findProfilePostsByMemberId(memberId);
    }

    // 게시물 상세 조회
    public PostDetailDto getPostDetail(Long postId, Long loginMemberId) {

        Post post = postRepository.findByIdWithMember(postId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 게시물입니다.")
        );

        // 작성자 정보
        Member author = post.getMember();

        List<PostImage> postImages = post.getImages();

        // 게시물에 보여줄 이미지 URL 순서 정렬해서 반환
        List<String> imageUrls = postImages.stream().sorted((a, b) -> a.getOrderNum().compareTo(b.getOrderNum()))
                .map(pi -> pi.getImageUrl())
                .collect(Collectors.toList());

        // 좋아요 수
        Long postLikeCount = postLikeRepository.countByPostId(post.getId());

        // 내가 좋아요 눌렀는
        boolean likedByMe = postLikeRepository.existsByPostIdAndMemberId(post.getId(), loginMemberId);

        // 내 게시물인지
        boolean myPost = author.getId().equals(loginMemberId);

        // 댓글 목록
        List<Comment> comments = commentRepository.findByPostIdOrderByCreatedAtAsc(post.getId());
        List<PostDetailDto.CommentDto> commentDtos = comments.stream()
                .map(c -> new PostDetailDto.CommentDto(
                        c.getId(), c.getMember().getName(), c.getMember().getProfileImage(),
                        c.getContent(), c.getCreatedAt(), c.getMember().getId().equals(loginMemberId)
                )).collect(Collectors.toList());

        log.info("게시물 불러오기 성공");
        return new PostDetailDto(
                author.getId(), author.getName(), author.getProfileImage(),
                post.getId(), post.getContent(), imageUrls, post.getCreatedAt(),
                postLikeCount, Long.valueOf(commentDtos.size()),
                likedByMe, myPost, commentDtos
        );
    }

    // 게시물에 좋아요(추가: true, 삭제: false)
    @Transactional
    public PostLikeResultDto toggleLike(Long memberId, Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시물입니다."));

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        boolean likedByMe;

        Optional<PostLike> exists = postLikeRepository.findByPostIdAndMemberId(postId, memberId);
        if (exists.isPresent()) {
            // 좋아요 있으면 삭제
            postLikeRepository.delete(exists.get());
            likedByMe = false;
        } else {
            // 좋아요 없으면 추가
            PostLike postLike = PostLike.createPostLike(post, member);
            postLikeRepository.save(postLike);
            likedByMe = true;
        }

        // 게시물 좋아요 수 조회
        Long likeCount = postLikeRepository.countByPostId(postId);

        return new PostLikeResultDto(likedByMe, likeCount);
    }

    @Transactional
    public void deletePost(Long postId, Long loginMemberId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 게시물입니다.")
        );

        if (!post.getMember().getId().equals(loginMemberId)) {
            throw new IllegalStateException("작성자만 삭제할 수 있습니다.");
        }
        postRepository.delete(post);
    }
}