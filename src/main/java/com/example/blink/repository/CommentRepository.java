package com.example.blink.repository;

import com.example.blink.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select c from Comment c " +
            "join fetch c.member " +
            "where c.post.id = :postId " +
            "order by c.createdAt asc")
    List<Comment> findByPostIdOrderByCreatedAtAsc(@Param("postId") Long postId);
}