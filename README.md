# IITP API

IITP API는 POI(Point of Interest) 데이터를 제공하는 RESTful API 서비스입니다.

## 기술 스택

- Java 17
- Spring Boot 3.2.3
- Spring Security
- Spring Data JPA
- PostgreSQL
- OpenAPI 3.0 (Stoplight Elements)
- Log4j2
- Jasypt (설정 파일 암호화)
- Bucket4j (Rate Limiting)

## 주요 기능

- POI 데이터 CRUD
- API Key 기반 인증
- Rate Limiting
- 버전 관리 (URL 패턴 방식)
- OpenAPI 문서화 (Stoplight Elements)

## 시작하기

### 필수 조건

- JDK 17
- PostgreSQL 12+
- Gradle 8+

### 설치 및 실행

1. 저장소 클론
```bash
git clone https://github.com/your-org/iitp-api.git
cd iitp-api
```

2. 환경 설정
```bash
# application-dev.yml 파일에서 데이터베이스 설정 확인
# 필요한 경우 Jasypt 암호화 키 설정
export JASYPT_ENCRYPTOR_PASSWORD=your-password
```

3. 빌드
```bash
# 개발 환경
./gradlew buildDev

# 스테이징 환경
./gradlew buildStage

# 프로덕션 환경
./gradlew buildProd
```

4. 실행
```bash
# 개발 환경
java -jar build/libs/iitp-api-0.0.1-SNAPSHOT-dev.jar

# 스테이징 환경
java -jar build/libs/iitp-api-0.0.1-SNAPSHOT-stage.jar

# 프로덕션 환경
java -jar build/libs/iitp-api-0.0.1-SNAPSHOT-prod.jar
```

## API 문서

API 문서는 Stoplight Elements를 통해 제공됩니다:
- OpenAPI 스펙: `/api-docs`
- API 문서 UI: Stoplight Elements에서 확인

## API 인증

모든 API 요청에는 유효한 API Key가 필요합니다:
- 헤더: `X-API-Key`
- 예시: `curl -H "X-API-Key: your-api-key" http://localhost:8080/api/v1/poi`

## Rate Limiting

- 기본 제한: 분당 100회 요청
- 초과 시: 429 Too Many Requests 응답

## 로깅

- 로그 파일: `logs/iitp-api.log`
- 로그 레벨: INFO (기본값)
- 로그 포맷: `%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n`

## 라이선스

Proprietary - All rights reserved

## 프로젝트 구조

```
src/main/java/com/iitp/
├── IitpApiApplication.java
├── config/
│   ├── OpenApiConfig.java
│   └── SecurityConfig.java
├── constant/
│   └── ApiConstants.java
├── controller/
│   ├── V1/
│   │   └── PoiController.java
│   └── V2/
│       └── PoiController.java
├── dto/
│   ├── common/
│   │   └── ApiResponse.java
│   └── PoiDto.java
├── entity/
│   ├── ApiClient.java
│   └── Poi.java
├── exception/
│   ├── ApiException.java
│   ├── ErrorCode.java
│   └── GlobalExceptionHandler.java
├── repository/
│   ├── ApiClientRepository.java
│   └── PoiRepository.java
├── security/
│   └── ApiKeyAuthenticationFilter.java
└── service/
    └── PoiService.java
```

## 주요 기능

- RESTful API 엔드포인트
- API Key 기반 인증
- URL 패턴 기반 버전 관리
- OpenAPI 3.0 문서화 (Stoplight Elements)
- Rate Limiting
- 로깅 (Log4j2)
- 설정 파일 암호화 (Jasypt)

## API 엔드포인트

### V1 API
- GET /api/v1/poi/search - POI 검색
- GET /api/v1/poi/{id} - POI 상세 조회
- POST /api/v1/poi - POI 생성
- PUT /api/v1/poi/{id} - POI 수정
- DELETE /api/v1/poi/{id} - POI 삭제

### V2 API
- GET /api/v2/poi/search - POI 검색 (페이지네이션 지원)
- GET /api/v2/poi/{id} - POI 상세 조회
- POST /api/v2/poi - POI 생성
- PUT /api/v2/poi/{id} - POI 수정
- DELETE /api/v2/poi/{id} - POI 삭제 