package com.msa.authservice.global.querydsl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.querydsl.jpa.impl.JPAQueryFactory;

@Configuration
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = "com.msa.authservice")
public class QueryDslConfig {
  @PersistenceContext
  private EntityManager entityManager;

  @Bean
  public JPAQueryFactory queryFactory() {
    return new JPAQueryFactory(entityManager);
  }
}
