package com.example.blink.repository.postlike;

import com.example.blink.domain.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    // 게시물에 달린 좋아요 수
    @Query("select count(pl) from PostLike pl where pl.post.id = :postId")
    Long countByPostId(@Param("postId") Long postId);

    // 게시물에 로그인 한 사람이 좋아요 눌렀는지 유무
    @Query("select case when (count(pl) > 0) then true else false end " +
            "from PostLike pl " +
            "where pl.post.id = :postId and pl.member.id = :memberId")
    boolean existsByPostIdAndMemberId(@Param("postId") Long postId, @Param("memberId") Long memberId);

    Optional<PostLike> findByPostIdAndMemberId(Long postId, Long memberId);

    // 게시물 다건 조회 시 게시물 당 좋아요 수 조회
    @Query("select new com.example.blink.repository.postlike.LikeCountDto(pl.post.id, count(pl)) " +
            "from PostLike pl " +
            "where pl.post.id in :postIds " +
            "group by pl.post.id")
    List<LikeCountDto> countByPostIds(@Param("postIds") List<Long> postIds);

    @Query("select pl.post.id from PostLike pl " +
            "where pl.post.id in :postIds " +
            "and pl.member.id = :loginMemberId")
    List<Long> findLikedByPostIds(@Param("postIds") List<Long> postIds,
                                  @Param("loginMemberId") Long loginMemberId);
}