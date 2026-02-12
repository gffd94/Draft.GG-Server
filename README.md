# 🎮 Draft.GG : E-Sports 내전 드래프트 플랫폼

> **"사다리 타기는 그만. 이제 실력으로 자신의 가치를 증명하세요."**
> 친구들과의 5:5 내전을 위한 **실시간 경매(Auction) 기반 팀 빌딩 & 전적 관리 서비스**입니다.

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-000000?style=for-the-badge&logo=mysql&logoColor=white)
![Redis](https://img.shields.io/badge/Redis-DC382D?style=for-the-badge&logo=redis&logoColor=white)

## 📅 Project Overview
* **프로젝트명:** Draft.GG
* **기획 배경:** 기존의 지루한 팀 나누기 방식에서 벗어나, 프로 리그의 스토브리그처럼 **플레이어를 경매 매물로 등록하고 팀장이 가상 화폐로 영입**하는 역동적인 경험을 제공하기 위해 기획했습니다.
* **핵심 목표:**
  * **동시성 제어:** 인기 매물 입찰 시 발생하는 Race Condition 해결.
  * **실시간성:** WebSocket을 활용한 초단위 호가 갱신 및 경매 현황 중계.
  * **대용량 트래픽:** Redis Stream을 활용한 입찰 로그 비동기 처리.

## 🛠️ Tech Stack & Architecture

### Backend
| Tech | Description |
| :--- | :--- |
| **Java 17** | Main Language |
| **Spring Boot 3.x** | Web Framework |
| **Spring Data JPA** | ORM (MySQL) |
| **Redis** | **Distributed Lock (Redisson)**, Caching, **Stream (Message Queue)** |
| **Spring Security** | OAuth2 Client (Discord Login) |
| **WebSocket (STOMP)** | Real-time Communication |

### Database
* **MySQL 8.0:** 회원 정보, 전적, 경매 결과 영구 저장.
* **Redis:** 실시간 경매 현황 캐싱, 분산 락, 대기열 관리.

## 🔥 Key Features & Technical Challenges

### 1. 동시성 제어 (Concurrency Control)
* **Situation:** 인기 플레이어(매물)에게 팀장 A, B가 동시에 입찰을 시도하는 상황.
* **Solution:** **Redis Distributed Lock (Redisson)**을 적용하여 입찰 트랜잭션의 원자성(Atomicity) 보장. 선착순 1명의 요청만 처리하고 나머지는 정합성 오류 없이 반려.

### 2. 실시간 경매 시스템 (Real-time Bidding)
* **Tech:** WebSocket + STOMP
* **Flow:** 입찰 발생 -> Redis Pub/Sub 발행 -> 구독 중인 모든 클라이언트(방 참가자)에게 호가 정보 브로드캐스팅 (Latency < 100ms 목표).

### 3. 대용량 트래픽 처리 (High Traffic)
* **Challenge:** 경매 종료 직전 몰리는 트래픽 부하 분산.
* **Solution:** **Redis Stream**을 활용하여 입찰 로그를 메모리에 먼저 적재(Write-Back)하고, 이후 비동기로 MySQL에 Bulk Insert하여 DB 부하 최소화.

## 🤝 Contribution & Convention

### Commit Message
* `feat`: 새로운 기능 추가
* `fix`: 버그 수정
* `refactor`: 코드 리팩토링
* `docs`: 문서 수정
* `test`: 테스트 코드 추가
* `chore`: 빌드 설정 및 기타 수정

### Branch Strategy
* `main`: 배포용 브랜치
* `develop`: 개발용 브랜치
* `feat/{issue-number}`: 단위 기능 개발 브랜치
