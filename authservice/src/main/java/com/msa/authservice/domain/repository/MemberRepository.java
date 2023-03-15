package com.msa.authservice.domain.repository;

import com.msa.authservice.domain.entity.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByMemberId(String memberId);

    Member findByMemberId(String memberId);

    Optional<Member> findOneMemberByMemberId(String memberId);

    @EntityGraph(attributePaths = "authorities")
    Optional<Member> findOneWithAuthoritiesByMemberId(String memberId);

}

