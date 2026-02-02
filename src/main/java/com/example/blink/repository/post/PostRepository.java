package com.example.blink.repository.post;

import com.example.blink.domain.Post;
import com.example.blink.service.post.response.ProfilePostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    // 게시물 수 조회
    @Query("select count(p) from Post p where p.member.id = :memberId")
    Long countPostsByMemberId(@Param("memberId") Long memberId);

    /**
     * 프로필 화면용 게시물 목록 조회
     * - 첫 번째 이미지만 조회
     * - 좋아요 수, 댓글 수 포함
     */
    @Query("select new com.example.blink.service.post.response.ProfilePostDto(" +
            "p.id, " +
            "(select pi.imageUrl from PostImage pi where pi.post = p and pi.orderNum = 0), " +
            "(select count(pl) from PostLike pl where pl.post = p), " +
            "(select count(c) from Comment c where c.post = p), " +
            "p.createdAt" +
            ") " +
            "from Post p " +
            "where p.member.id = :memberId " +
            "order by p.createdAt desc")
    List<ProfilePostDto> findProfilePostsByMemberId(@Param("memberId") Long memberId);

    // 게시물 상세 조회 (작성자 정보 포함)
    @Query("select p from Post p " +
            "join fetch p.member " +
            "where p.id = :postId")
    Optional<Post> findByIdWithMember(@Param("postId") Long postId);

    // 피드 화면 게시물 목록 조회
    @Query("select p from Post p " +
            "join fetch p.member m " +
            "order by p.createdAt desc")
    Page<Post> findAllWithMember(Pageable pageable);
}