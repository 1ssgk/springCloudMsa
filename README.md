
# Spring cloud 환경 공부

공부로 이것저것 다해보는 중..



## 1. apigateway

Spring Cloud Apigateway 서버.

외부망에서 접속이 가능하게 열어놓고 사용하면 될것 같다.
- globalfilter 를 이용하여 access token 검증

</br>
</br>

## 2. user-service ( 사용 X )

Spring OAuth2 를 이용한 로그인 인증방식을 구현한 서버이다.
현재 google oauth 연동

</br>
</br>

## 3. eurekaserver

Spring Cloud Netflix Eureka Server 이다.

Discovery 서버로서 서비스에서 사용하는 서버들의 목적지를 관리하는 서버

</br>
</br>

## 4. configserver

Spring Cloud Config Server 이다.

git repository 에 작성되어있는 각 서버의 application.yml를

각각의 서버에 설정을 해줌으로서 config 서버와 연결되어있는 설정파일들로 서버를 실행할 수 있다.

@ 현재 Spring Cloud Bus + RabbitMQ + Spring Actuator 를 이용한 

</br>

예제)
```c
spring:
  application:
    name: apigateway-service  // 어플리케이션 이름
  profiles:
    active: local  // 프로파일
  config:
    import: optional:configserver:{Config서버IP:PORT}     // http://localhost:8581

```
위 예제로 작성되면 **config 서버**와 연결되어있는 **git repository** 에서 </br>
**apigateway-service-local.yml** 의 파일의 설정으로 서버가 실행된다.


## 5. auth-server

토큰 발급 및 재발급을 하는 서버 이다.

현재 google api client를 이용하여 인증 통과 시 토큰을 발급해주는 방향으로 만들어져있다.

spring rest docs 와 swagger-ui를 이용한 api 문서 작성도 해보고 있다.

swagger-ui는 로컬에 도커를 이용.
swagger-ui서버를 따로 띄워 각 서버의 문서들을 보아 보는형태.



