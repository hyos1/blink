package com.example.blink.repository;

import com.example.blink.domain.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    @Query("select count(pl) from PostLike pl where pl.post.id = :postId")
    Long countByPostId(@Param("postId") Long postId);

    @Query("select case when (count(pl) > 0) then true else false end " +
            "from PostLike pl " +
            "where pl.post.id = :postId and pl.member.id = :memberId")
    boolean existsByPostIdAndMemberId(@Param("postId") Long postId,@Param("memberId") Long memberId);
}