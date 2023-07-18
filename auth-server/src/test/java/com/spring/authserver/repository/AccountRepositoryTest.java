package com.spring.authserver.repository;

import com.spring.authserver.entity.Account;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;


@DataJpaTest
class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @Test
    @DisplayName("계정 생성")
    public void create() {
        //given
        final Account account = Account.builder()
                .lastName("김")
                .firstName("아무개")
                .email("test111@naver.com")
                .socialId("GOOGLE")
                .roles("ROLE_USER")
                .imageUrl("imageURL")
                .build();

        //when
        final Account result = accountRepository.save(account);

        //then
        assertThat(result.getId()).isNotNull();
        assertThat(result.getEmail()).isNotNull();
        assertThat(result.getRoles()).isNotNull();
    }

    @Test
    @DisplayName("이메일로 계정 찾기")
    public void exist() {
        //given
        final Account account = Account.builder()
                .lastName("김")
                .firstName("아무개")
                .email("test111@naver.com")
                .socialId("GOOGLE")
                .roles("ROLE_USER")
                .imageUrl("imageURL")
                .build();

        //when
        accountRepository.save(account);
        final Optional<Account> findResult = accountRepository.findByEmail("test111@naver.com");

        //then
        assertThat(findResult.get().getId()).isNotNull();
        assertThat(findResult.get().getEmail()).isEqualTo("test111@naver.com");
    }


}