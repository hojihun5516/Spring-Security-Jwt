# Spring Security With Jwt

## 목적
스프링 시큐리티 JWT 구현

## 내용
- 스프링 시큐리티와 JWT를 통해 간단한 인증과 인가를 구현
- 권한은 ADMIN, USER가 있음
- docker-compose를 사용하여 도커 컨테이너를 사용
- Exception Handler를 프로젝트에 맞게 구현하여 사용
- 여러 개의 키를 사용하여 accessToken을 만드는 key rolling기술을 사용
- 로그인 할 때 원하는 Role을 선택하여 로그인한다
  - [joinUsernameAndRole] 함수를 활용하여 [Userdetails] 클래스의 [loadUserByUsername] 메소드에 전달되는 [username]에 원하는 ROLE을 포함
- User는 여러개의 Profile을 갖고 있을 수 있고 각 Profile은 하나의 권한을 가질 수 있다

## 실행 방법
``docker-compose build --no-cache & docker-compose up``
