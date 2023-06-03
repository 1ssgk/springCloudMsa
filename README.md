
# Spring cloud 환경 공부



## 1. apigateway

Spring Cloud Apigateway 서버.

외부망에서 접속이 가능하게 열어놓고 사용하면 될것 같다.

- 해야할것
</br> 
user-service에서 로그인이 성공한 경우 제공되는 jwt-token에 대한 필터 설정 해야함.

</br>
</br>

## 2. user-service

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



