# backend-develop-onboarding-project

## Wiki 바로가기

- [Spring Security 기본 이해](https://github.com/kyeonkim/backend-develop-onboarding-project/wiki#spring-security-%EA%B8%B0%EB%B3%B8-%EC%9D%B4%ED%95%B4)
  - [Filter란 무엇인가?(with Interceptor, AOP)](https://github.com/kyeonkim/backend-develop-onboarding-project/wiki#filter%EB%9E%80-%EB%AC%B4%EC%97%87%EC%9D%B8%EA%B0%80with-interceptor-aop)
  - [Spring Security란?](https://github.com/kyeonkim/backend-develop-onboarding-project/wiki#spring-security%EB%9E%80)
- [JWT 기본 이해](https://github.com/kyeonkim/backend-develop-onboarding-project/wiki#jwt-%EA%B8%B0%EB%B3%B8-%EC%9D%B4%ED%95%B4)
  - [JWT란 무엇인가요?](https://github.com/kyeonkim/backend-develop-onboarding-project/wiki#jwt%EB%9E%80-%EB%AC%B4%EC%97%87%EC%9D%B8%EA%B0%80%EC%9A%94)
  - [Access / Refresh Token](https://github.com/kyeonkim/backend-develop-onboarding-project/wiki#access--refresh-token)
  - [Access / Refresh Token 발행과 검증에 관한 테스트 시나리오](https://github.com/kyeonkim/backend-develop-onboarding-project/wiki#access--refresh-token-%EB%B0%9C%ED%96%89%EA%B3%BC-%EA%B2%80%EC%A6%9D%EC%97%90-%EA%B4%80%ED%95%9C-%ED%85%8C%EC%8A%A4%ED%8A%B8-%EC%8B%9C%EB%82%98%EB%A6%AC%EC%98%A4)

## 배포

- http://3.34.26.120:8080

## 배포 환경

- 서버: AWS EC2(프리티어)
- DB: mysql

> 현재 DB는 RDS를 사용하지 않고 EC2 내에 docker를 설치하고 공식 mysql 이미지를 이용하여 컨테이너로 띄운 후 사용하고 있습니다. 이렇게 사용하는 이유는 EC2에서 RDS로 연결하기 위해 public IPv4 주소를 사용해야 하는데 2024년 2월 1일부터 'Public IPv4 주소 사용이 유료화'가 되어 비용이 발생하기 때문입니다. 

## 설치 및 실행

1. git clone 진행
```markdown
https://github.com/kyeonkim/backend-develop-onboarding-project.git
```

2. onboarding 폴더 내에 .env 파일 생성 후 [ ] 부분 작성
```markdown
MYSQL_ROOT_PASSWORD=[루트_사용자_비밀번호(ex:rootpassword)]
MYSQL_DATABASE=[사용할_데이터_베이스_이름(ex:userdb)]
MYSQL_USER=[사용할_유저_이름(ex:user)]
MYSQL_PASSWORD=[해당_유저_비밀번호(ex:userpassword)]
```

3. docker 설치 후 폴더 내에 docker compose 실행
```markdown
docker compose up -d
```

4. onboarding/src/main/resources/application.yml 파일에 [ ] 부분 작성
```markdown
spring:
  application:
    name: onboarding
  datasource:
    url: jdbc:mysql://localhost:[DB_PORT(ex:3306)]/[사용할_데이터_베이스_이름]
    username: [사용할_유저_이름]
    password: [해당_유저_비밀번호]
    driver-class-name: com.mysql.cj.jdbc.Driver
  jpa:
    hibernate:
      ddl-auto: update
    properties:
      hibernate:
        dialect: org.hibernate.dialect.MySQLDialect

jwt:
  secretKey: [secretKey(ex:kyeonkim1234123412341234123412341234)]
  access-token-expiration: 60000
  refresh-token-expiration: 1800000
```

5. onboarding 폴더내에 gradlew 파일을 이용하여 app 실행(CLI 이용)
```markdown
./gradlew bootRun
```

## 테스트

1. Swagger UI 이용: /api-test(ex: http://localhost:8080/api-test)
2. Postman과 같이 API 요청 테스트 툴 이용
##### Request 예시
```markdown
[url]
  1. 로컬: localhost
  2. 배포된_사이트: 3.34.26.120

1. 회원가입(/signup)

POST http://[url]:8080/signup
Content-Type: application/json

{
  "username": "kyeonkim",
  "password": "asdf1234",
  "nickname": "Man"
}

2. 로그인(/sign)

POST http://[url]:8080/sign
Content-Type: application/json

{
  "username": "kyeonkim",
  "password": "asdf1234"
}
```
