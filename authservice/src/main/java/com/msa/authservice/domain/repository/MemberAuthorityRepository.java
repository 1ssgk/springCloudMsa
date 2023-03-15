package com.msa.authservice.domain.repository;

import com.msa.authservice.domain.entity.MemberAuthority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberAuthorityRepository extends JpaRepository<MemberAuthority, Long> {

}
