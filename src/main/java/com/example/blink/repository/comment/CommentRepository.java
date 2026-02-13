package com.example.blink.repository.comment;

import com.example.blink.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select c from Comment c " +
            "join fetch c.member " +
            "where c.post.id = :postId " +
            "order by c.createdAt asc")
    List<Comment> findAllWithMemberByPostIdOrderByCreatedAtAsc(@Param("postId") Long postId);

    // PostId 별 댓글 수 조회
    @Query("select new com.example.blink.repository.comment.CommentCountDto(c.post.id, count(c)) " +
            "from Comment c " +
            "where c.post.id in :postIds " +
            "group by c.post.id")
    List<CommentCountDto> countByPostIds(@Param("postIds") List<Long> postIds);
}