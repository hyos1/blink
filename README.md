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

### 성능 측정 방법

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
