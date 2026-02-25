# 🌟 Blink - SNS 피드 서비스

Instagram 스타일의 소셜 미디어 플랫폼입니다.
<br/>
SSR 기반 화면을 추가하여 구현한 API의 동작을 확인하고 <br/>
성능 최적화, AWS(EC2, RDS, S3) 배포까지 경험하기 위해 만든 개인 프로젝트입니다. 

[![Tistory](https://img.shields.io/badge/Blog-FF5722?style=flat-square&logo=blogger)](https://hyodicube.tistory.com/)
[![Notion](https://img.shields.io/badge/Notion-000000?style=flat-square&logo=notion&logoColor=white)](https://www.notion.so/Blink-SNS-2df27b52447580dd89c2d6f05fba8a79)

### 프로젝트 개요

- **개발 기간**: 2025.12.07 ~ 2026.02.20
- **개발 인원**: 1인 개발
- **배포 환경**: AWS (EC2, RDS, S3)

### 주요 성과

- **SSR 기반 화면을 추가**하여 구현한 API의 동작 흐름 확인
- **동시 사용자 100명 기준 평균 응답속도 61% 개선** (124ms → 48ms)
- **동시 사용자 200명 기준 처리량 55% 향상** (424 req/s → 658 req/s)
- **JMeter 부하 테스트**로 성능 개선 효과 검증
- **AWS EC2(Spring Boot), RDS(MySQL), S3** 기반 인프라 설계 및 배포


### 🏗 아키텍처

### 시스템 구조
```
┌─────────────┐
│   사용자     │
└──────┬──────┘
       │ HTTP/HTTPS
       ▼
┌─────────────────────────┐
│  AWS EC2 (Ubuntu 24.04) │
│  Spring Boot App        │
└──────┬─────────┬────────┘
       │         │
       ▼         ▼
┌──────────┐ ┌─────────┐
│ AWS RDS  │ │ AWS S3  │
│ (MySQL)  │ │ (이미지) │
└──────────┘ └─────────┘
```

## ERD
<img width="1049" height="426" alt="Image" src="https://github.com/user-attachments/assets/89bab289-26e6-4362-8769-56131332e4ad" />


## 실행영상
[실행 영상](https://github.com/user-attachments/assets/51e25fe1-7a4b-4750-ac71-3e5d5ca7819b)


## [주요 기능]

### 1. 회원 관리
- 회원가입/로그인 (Session 기반 인증)
- 프로필 조회 (마이 프로필 / 타 사용자 프로필)
- 회원 검색 (입력 0.3초 후)

### 2. 소셜 기능
- 팔로우/언팔로우
- 팔로워/팔로잉 목록 조회
- 회원 추천 (팔로우 하지 않은 사람)

### 3. 게시물
- 게시물 작성 (다중 이미지 업로드, 최소 1장, 최대 3장)
- 이미지 슬라이더
- 게시물 삭제
- 무한 스크롤 피드

### 4. 상호작용
- 좋아요/좋아요 취소
- 댓글 작성/삭제
- 실시간 좋아요/댓글 수 반영
<br/>

## [추가로 구현한다면]
- 사용자 간 1:1 메세지 기능
- 좋아요/댓글/팔로우 발생 시 알림(Notification) 기능
<br/>

## [기술 선택의 이유]

### 인증 방식
SSR 방식 단일 서버 프로젝트이므로,<br/>
서버 확장이 필요한 환경에서 유리한 JWT 대신 <br/>
서버에서 로그인 상태를 관리하는 Session 방식을 선택

### 웹/도메인 계층 분리
웹 계층 변경에도 도메인 비즈니스 로직이 최대한 영향받지 않도록 설계

### Filter / Interceptor
로그인 여부 확인, 정상/예외 로그를 상세히 처리하기 위해 <br/>
ServletRequest/Response만 사용하는 Filter 대신 <br/> 
Handler 정보와 ModelAndView를 활용하는 Interceptor를 적용

### 파일 저장 방식
개발 초기에는 로컬 디스크에 저장하였으나, <br/>
배포 서버 장애 시 파일 유실 위험 및 용량 한계를 고려하여 <br/>
AWS S3도입으로 애플리케이션 서버와 파일 저장소 분리

## 성능 최적화
### [1차 최적화: 피드 조회]
**문제**
- 무한 스크롤에 필요한 Count 쿼리 발생
- 좋아요/댓글 수만 필요한데 전체 엔티티 로딩

**해결**
- Page -> Slice로 전환
- 게시물 ID들 추출하여 집계 쿼리로 변경
- Batch Size 적용으로 지연로딩 쿼리 N -> 1

### [2차 최적화: 게시물 상세 조회]
**문제**
- PostImage가 Batch Size로 추가 쿼리 발생
- 이미지 순서 정렬을 어플리케이션에서 처리
- 게시물 기준 이미지(N), 댓글(N) 중 fetch join 적용 필요

**해결**
- 컬렉션 fetch join 시 자식 수만큼 루트 엔티티 중복 고려하여 <br/>
  개수 제한 없는 댓글 대신 이미지(최대 3장)를 fetch join 대상으로 선택
- Post + Member + PostImage fetch join - (이미지 순서 DB에서 처리)
- 댓글에 필요한 내용 DTO Projection 처리
<br/>


## 성능 측정 방법
**JMeter 부하 테스트**
- Thread: 50 / 100 / 200 (동시 사용자)
- 각 환경에서 5회 반복 측정
- **중앙값** 기준으로 성능 개선 효과 검증

**최종 성과**

| Thread | 측정 항목 | 최적화 전 | 최적화 후 | 개선율 |
|--------|----------|----------|----------|--------|
| 100 | Total Average | 124ms | 48ms | **61% 감소** |
| 200 | Total Average | 364ms | 196ms | **46% 감소** |
| 200 | Total Throughput | 424 req/s | 658 req/s | **55% 증가** |

자세한 성능 테스트 과정은 [블로그](https://hyodicube.tistory.com/84), 
요약한 과정은 [Notion](https://www.notion.so/Blink-SNS-2df27b52447580dd89c2d6f05fba8a79)에 작성하였습니다.
