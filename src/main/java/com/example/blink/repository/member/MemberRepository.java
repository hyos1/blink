package com.example.blink.repository.member;

import com.example.blink.domain.Member;
import com.example.blink.service.member.response.MemberProfileDto;
import com.example.blink.service.member.response.MemberSidebarDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("select m.id from Member m where m.name = :name")
    Optional<Long> findIdByName(@Param("name") String name);

    Optional<Member> findByEmail(String email);

    boolean existsByName(String name);

    boolean existsByEmail(String email);

    @Query("select new com.example.blink.service.member.response.MemberSidebarDto(" +
            "m.id, m.name, m.email, m.profileImage, " +
            "(select count(p) from Post p where p.member = m), " +
            "(select count(f) from Follow f where f.following = m), " +
            "(select count(f) from Follow f where f.follower = m)" +
            ") " +
            "from Member m " +
            "where m.id = :memberId")
    Optional<MemberSidebarDto> findSidebarInfoById(@Param("memberId") Long memberId);

    // 기본 프로필 정보만 조회
    @Query("select new com.example.blink.service.member.response.MemberProfileDto(" +
            "m.id, m.name, m.profileImage ,m.bio" +
            ") " +
            "from Member m " +
            "where m.id = :memberId")
    Optional<MemberProfileDto> findProfileById(@Param("memberId") Long memberId);

    // 이름으로 회원 검색 (대소문자 구분 없이, 회원 이름 앞 뒤에 %붙힌 Like 검색)
    List<Member> findByNameContainingIgnoreCase(@Param("query") String query, Pageable pageable);
}