package com.msa.authservice.domain.entity;

import com.msa.authservice.global.entity.BaseTimeEntity;
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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;


@Entity
@Table(name = "MEMBER")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Member extends BaseTimeEntity {
    @Id
    @Column(name = "MEMBER_KEY")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberKey;

    @Column(name = "MEMBER_ID",unique = true)
    private String memberId;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "MEMBER_NM")
    private String memberNm;

    @Column(name = "REG_NO", unique = true)
    private String regNo;

    @ManyToMany
    @JoinTable(
            name = "MEMBER_AUTHORITY",
            joinColumns = {@JoinColumn(name = "MEMBER_KEY", referencedColumnName = "MEMBER_KEY")},
            inverseJoinColumns = {@JoinColumn(name = "AUTHORITY_NAME", referencedColumnName = "AUTHORITY_NAME")})
    private Set<Authority> authorities;
}
