package com.msa.authservice.domain.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MEMBER_AUTHORITY")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class MemberAuthority {
    @Id
    @Column(name = "MEMBER_KEY")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberKey;

    @Column(name = "AUTHORITY_NAME")
    private String authorityName;


}
