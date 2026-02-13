package com.example.blink.service.post;

import com.example.blink.domain.*;
import com.example.blink.exception.ClientException;
import com.example.blink.exhandler.ErrorCode;
import com.example.blink.file.request.UploadFile;
import com.example.blink.repository.comment.CommentRepository;
import com.example.blink.repository.comment.CommentCountDto;
import com.example.blink.repository.member.MemberRepository;
import com.example.blink.repository.post.PostRepository;
import com.example.blink.repository.post.query.FeedPostDto;
import com.example.blink.repository.postlike.LikeCountDto;
import com.example.blink.repository.postlike.PostLikeRepository;
import com.example.blink.service.post.request.CreatePostCommand;
import com.example.blink.service.post.response.PostDetailDto;
import com.example.blink.service.post.response.PostLikeResultDto;
import com.example.blink.service.post.response.ProfilePostDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static com.example.blink.exhandler.ErrorCode.*;

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
                () -> new ClientException(USER_NOT_FOUND)
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
                () -> new ClientException(POST_NOT_FOUND)
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
        List<Comment> comments = commentRepository.findAllWithMemberByPostIdOrderByCreatedAtAsc(post.getId());
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
                .orElseThrow(() -> new ClientException(POST_NOT_FOUND));

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ClientException(USER_NOT_FOUND));

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
                () -> new ClientException(POST_NOT_FOUND)
        );

        if (!post.getMember().getId().equals(loginMemberId)) {
            throw new ClientException(POST_DELETE_FORBIDDEN); // 본인 게시물만 삭제 가능
        }
        postRepository.delete(post);
    }

    // Feed 화면에 보여줄 게시물
//    public Page<FeedPostDto> getFeedPosts(Long loginMemberId, int page, int size) {
//
//        PageRequest pageRequest = PageRequest.of(page, size);
//        Slice<Post> posts = postRepository.findAllWithMember(pageRequest);
//        if (posts.isEmpty()) {
//            return Page.empty();
//        }
//
//        return posts.map(post -> {
//            // 게시물 이미지 URL 추출
//            List<String> imageUrls = new ArrayList<>();
//            for (PostImage image : post.getImages()) {
//                imageUrls.add(image.getImageUrl());
//            }
//
//            // 좋아요 수
//            Long postLikeCount = (long) post.getPostLikes().size();
//
//            // 댓글 수
//            Long postCommentCount = (long) post.getComments().size();
//
//            boolean likedByMe = false;
//
//            for (PostLike postLike : post.getPostLikes()) {
//                if (postLike.getMember().getId().equals(loginMemberId)) {
//                    likedByMe = true;
//                    break;
//                }
//            }
//
//            return new FeedPostDto(
//                    post.getId(),
//                    post.getContent(),
//                    post.getCreatedAt(),
//                    post.getMember().getId(),
//                    post.getMember().getName(),
//                    post.getMember().getProfileImage(),
//                    imageUrls,
//                    postLikeCount,
//                    postCommentCount,
//                    likedByMe
//            );
//        });
//    }

    public Slice<FeedPostDto> getFeedPosts(Long loginMemberId, int page, int size) {

        PageRequest pageRequest = PageRequest.of(page, size);
        Slice<Post> posts = postRepository.findFeedPostsWithMember(pageRequest);

        if (!posts.hasContent()) {
            return new SliceImpl<>(Collections.emptyList(), pageRequest, false);
        }

        // 게시물 ID들 추출
        List<Long> postIds = posts.stream().map(p -> p.getId()).collect(Collectors.toList());

        // PostId 별 좋아요 수 추출
        List<LikeCountDto> likeCountDtos = postLikeRepository.countByPostIds(postIds);
        Map<Long, Long> likeCountMap = new HashMap<>();
        for (LikeCountDto likeCountDto : likeCountDtos) {
            likeCountMap.put(likeCountDto.getPostId(), likeCountDto.getLikeCount());
        }

        // PostId 별 댓글 수 추출
        List<CommentCountDto> commentCountDtos = commentRepository.countByPostIds(postIds);
        Map<Long, Long> commentCountMap = new HashMap<>();
        for (CommentCountDto commentCountDto : commentCountDtos) {
            commentCountMap.put(commentCountDto.getPostId(), commentCountDto.getCommentCount());
        }

        // 로그인 사용자가 추출한 PostId 중 좋아요 누른 게시물 ID들
        List<Long> likedByPostIds = postLikeRepository.findLikedByPostIds(postIds, loginMemberId);

        List<FeedPostDto> content = posts.getContent().stream().map(post -> {
            // 게시물 이미지 URL 추출
            List<String> imageUrls = new ArrayList<>();
            for (PostImage image : post.getImages()) {
                imageUrls.add(image.getImageUrl());
            }

            // 좋아요 수
            Long postLikeCount = likeCountMap.getOrDefault(post.getId(), 0L);

            // 댓글 수
            Long postCommentCount = commentCountMap.getOrDefault(post.getId(), 0L);

            // 요청한 사람이 좋아요 눌렀는지 유무
            boolean likedByMe = likedByPostIds.contains(post.getId());


            return new FeedPostDto(
                    post.getId(),
                    post.getContent(),
                    post.getCreatedAt(),
                    post.getMember().getId(),
                    post.getMember().getName(),
                    post.getMember().getProfileImage(),
                    imageUrls,
                    postLikeCount,
                    postCommentCount,
                    likedByMe
            );
        }).collect(Collectors.toList());

        return new SliceImpl<>(content, pageRequest, posts.hasNext());
    }

}