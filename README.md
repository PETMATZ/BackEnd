# 🐕 PETMATZ BackEnd

> 데브코스 최종 프로젝트 11팀 - 반려견 돌봄 플랫폼

PETMATZ는 반려견 소유자와 펫시터를 연결하는 안전하고 신뢰할 수 있는 돌봄 플랫폼입니다. 이 레포지토리는 PETMATZ 서비스의 백엔드 API 서버를 담고 있습니다.

## 📋 목차

- [프로젝트 소개](#프로젝트-소개)
- [주요 기능](#주요-기능)
- [기술 스택](#기술-스택)
- [프로젝트 구조](#프로젝트-구조)
- [설치 및 실행](#설치-및-실행)
- [API 문서](#api-문서)

## 🎯 프로젝트 소개

PETMATZ는 반려견 소유자가 안심하고 반려견을 맡길 수 있는 펫시터를 찾을 수 있는 플랫폼입니다. 
1:1 매칭 시스템, 실시간 채팅, 미션 기반 펫케어 서비스 등을 통해 신뢰할 수 있는 펫케어 서비스를 제공합니다.

### 🎯 프로젝트 목표
- 안전하고 신뢰할 수 있는 펫시터 매칭 서비스 제공
- 
- 반려견 소유자의 외출/여행 시 걱정 해소
- 커뮤니티를 통한 반려견 문화 확산

## ✨ 주요 기능

### 🔐 JWT를 이용한 로그인
- Spring Security와 JWT를 활용한 안전한 인증 시스템
- 액세스 토큰 및 리프레시 토큰 기반 인증
- 사용자 권한별 접근 제어

### 💬 1:1 채팅 기능
- 실시간 채팅 시스템
- 반려견 소유자와 펫시터 간 직접 소통
- 메시지 전송 기능

### 🤝 반려인과 펫시터의 1:1 매칭
- 지역 기반 펫시터 매칭
- 사용자 프로필 및 선호도 기반 추천
- 매칭 성공률 최적화 알고리즘

### 📋 펫 미션 등록
- 반려견 돌봄 미션 생성 및 관리
- 미션별 상세 요구사항 설정
- 미션 완료 상태 추적

### 🏘 커뮤니티 (게시판, 펫시터 구인 공고)
- 자유 게시판을 통한 정보 공유
- 펫시터 구인/구직 공고 시스템

### 🏆 랭킹 시스템
- 펫시터 평점 및 리뷰 기반 랭킹

### ❤️ 사용자 찜하기
- 선호하는 펫시터 찜하기 기능
- 찜한 펫시터 목록 관리

## 🛠 기술 스택
### Backend Framework & Language
- **Language**: Java 17
- **Framework**: Spring Boot 3.x
- **Security**: Spring Security + JWT

### Database & Cache
- **Main Database**: MySQL
- **Document Database**: MongoDB
- **Cache**: Redis

### Communication & Integration
- **HTTP Client**: OpenFeign
- **Real-time Communication**: WebSocket
- **Message Queue**: Redis Pub/Sub

### Development & Build Tools
- **Build Tool**: Gradle
- **Java Version**: 17
- **IDE**: IntelliJ IDEA

### DevOps & Infrastructure
- **Container**: Docker
- **Cloud**: AWS
- **CI/CD**: GitHub Actions

## 🏗 프로젝트 구조

```
src/main/java/com/petmatz/
├── api/                    # REST API 컨트롤러
├── application/            # 애플리케이션 서비스 계층
├── common/                 # 공통 유틸리티 및 설정
├── domain/                 # 도메인 모델 및 비즈니스 로직
├── infra/                  # 외부 서비스 연동 (DB, 외부 API)
├── persistence/            # 데이터 접근 계층 (Repository)
└── PetmetzApplication.java # 메인 애플리케이션 클래스
```

### 주요 디렉토리 설명

- **api**: REST API 엔드포인트 정의
- **application**: 비즈니스 로직 처리 서비스
- **common**: 전역 설정, 유틸리티, 예외 처리
- **domain**: 엔티티, DTO, 도메인 서비스
- **infra**: 외부 서비스 연동 (AWS, 외부 API)
- **persistence**: JPA Repository, MongoDB Repository

## 🚀 설치 및 실행
### 필수 요구사항
- Java 17+
- MySQL 8.0+
- MongoDB 4.4+
- Redis 6.0+

### 개발 환경 설정
**레포지토리 클론**
```bash
git clone https://github.com/LJW22222/PETMATZ.git
cd PETMATZ
```

**환경 변수 설정**
```bash
cp application.yml.example application.yml
# application.yml 파일을 편집하여 필요한 환경 변수 설정
```

**애플리케이션 실행**
```bash
# 또는 JAR 파일 빌드 후 실행
./gradlew build
java -jar build/libs/petmatz-0.0.1-SNAPSHOT.jar
```

**애플리케이션 접속**
- API 서버: http://localhost:8080

**API 문서**
- Swagger UI: http://localhost:8080/swagger-ui.html


---
**PETMATZ** - 반려견과 함께하는 안전한 돌봄 플랫폼 🐕
