# Spring Security With Jwt

## 목적
- 하나의 계정정보로 여러개의 프로필을 사용하기
- ex) 하나의 서비스에 **판매자로 로그인**, **구매자로 로그인** 등의 멀티 프로필 환경을 가정

## 개선사항
ASIS
- 기존의 많은 로그인 방식은 profile이란 개념이 들어있지 않아서 하나의 user 정보에 판매자, 구매자의 권한정보를 모두 저장

TOBE
- 같은 로그인 계정 정보로 여러개의 프로필을 만들어서 사용

- 로그인 시점에 사용할 프로필 정보를 받아서 로그인 함

![user_profiles](images/user_profiles.png)

## 구현 내용
- 스프링 시큐리티와 JWT를 통해 간단한 인증과 인가를 구현
- 권한은 ADMIN, USER가 있음
- docker-compose를 사용하여 도커 컨테이너를 사용
- Exception Handler를 프로젝트에 맞게 구현하여 사용
- 여러 개의 키를 사용하여 accessToken을 만드는 key rolling기술을 사용
- 로그인 할 때 원하는 Role을 선택하여 로그인한다
  - [joinUsernameAndRole] 함수를 활용하여 [Userdetails] 클래스의 [loadUserByUsername] 메소드에 전달되는 [username]에 원하는 ROLE을 포함
- User는 여러개의 Profile을 갖고 있을 수 있고 각 Profile은 하나의 권한을 가질 수 있다

## 실행 방법

```
# project build
./gradlew clean build

# docker build and run
docker-compose build --no-cache & docker-compose up
```
