# spring-gift-product
- ---
## 개요
- 카카오 테크 캠퍼스 과정 중, 카카오톡 선물하기 서비스 카피 프로젝트를 기록한 리포지토리
- ---
## 기술 스택
- Language : Java 21
- Framework : Spring Boot 3
- DB : H2 휘발성 DB
- Security : JWT, Oauth ( Kakao API )
- ORM : Spring Data JPA
- Cloud : AWS EC2 Free tier
- Front ( Admin Page ) : Vanilla JS + AJAX + JQuery
- ---
## 주요 구현 내용
- 도메인 : Product, User, WishProduct, Category, Option, Order
- 주요 기능
  - 각 도메인에 대한 CRUD 기능 ( RESTful한 형식으로 작성 )
  - 자체 로그인 기능 ( 유저 토큰 발급 및 필터링 기능 )
  - OAuth를 도입한 카카오 로그인 기능 ( 사용 시간이 적은 서비스 이기에 리프레시 토큰 로직은 포함하지 않음 )
  - 도메인 별 제약 조건에 따른 Validation 기능 ( Controller 계층 DTO에서 검증 )
  - 빌드 자동화를 위한 Shell 스크립트 ( **배포 자동화는 실패** )
  - 테스트와 관리를 위한 관리자 페이지 구현 ( AJAX )
- ---
