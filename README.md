# IITP API Service

## 프로젝트 개요
IITP API 서비스는 OpenAPI 형태로 데이터를 제공하는 RESTful API 서버입니다. 이 서비스는 API Key 기반의 인증을 사용하며, Rate Limiting을 통해 API 사용량을 제어합니다.

## 기술 스택
- Java 21
- Spring Boot 3.2.5
- Spring Security
- Spring Data JPA
- QueryDSL 5.0.0
- PostgreSQL
- Bucket4j 8.7.0 (Rate Limiting)
- SpringDoc OpenAPI 2.3.0
- Micrometer (Prometheus)
- Gradle 8+
- Lombok

## 주요 기능

### API 데이터 처리
- RESTful API 엔드포인트 제공
- 데이터 CRUD 작업 지원
- 페이징 및 정렬 기능
- 동적 쿼리 처리 (QueryDSL)
- 데이터 검증 및 변환
- 에러 처리 및 응답 표준화
- API 버전 관리 (URL 패턴 방식)

### 보안 및 제어
- API Key 기반 인증
- Rate Limiting
- IP 기반 접근 제어
- 요청 로깅 및 모니터링

### 문서화 및 모니터링
- OpenAPI 3.0 문서화
- Prometheus 메트릭 수집
- Actuator 모니터링
- API 사용량 통계

## API 엔드포인트

### 기본 API
- GET /api/v1/{resource} - 리소스 목록 조회
- GET /api/v1/{resource}/{id} - 단일 리소스 조회
- POST /api/v1/{resource} - 리소스 생성
- PUT /api/v1/{resource}/{id} - 리소스 수정
- DELETE /api/v1/{resource}/{id} - 리소스 삭제

### 고급 기능
- GET /api/v1/{resource}/search - 고급 검색
- GET /api/v1/{resource}/export - 데이터 내보내기
- POST /api/v1/{resource}/batch - 일괄 처리

### 공통 기능
- 페이징: `page`, `size` 파라미터
- 정렬: `sort` 파라미터
- 필터링: `filter` 파라미터
- 검색: `q` 파라미터

## 응답 형식
```json
{
    "status": "SUCCESS",
    "data": {
        // 응답 데이터
    },
    "metadata": {
        "page": 0,
        "size": 10,
        "totalElements": 100,
        "totalPages": 10
    }
}
```

## 에러 응답
```json
{
    "status": "ERROR",
    "error": {
        "code": "ERROR_CODE",
        "message": "에러 메시지",
        "details": []
    }
}
```

## 프로젝트 구조
```
src/main/java/com/sweetk/iitp/api/
├── config/                 # 설정 클래스
├── constant/              # 상수 정의
├── controller/            # API 컨트롤러
├── dto/                   # Data Transfer Objects
├── entity/                # JPA 엔티티
├── exception/             # 예외 처리
├── repository/            # 데이터 접근 계층
├── security/              # 보안 관련 클래스
├── service/               # 비즈니스 로직
└── util/                  # 유틸리티 클래스
```

## 보안
- API Key 인증: 모든 API 요청은 유효한 API Key가 필요합니다.
- Rate Limiting: IP 기반으로 API 요청 횟수를 제한합니다.
- HTTPS: 모든 통신은 HTTPS를 통해 암호화됩니다.

## API 문서
- OpenAPI 문서: `/v3/api-docs`
- API 문서 파일: `/docs/latest.yaml`

## 개발 환경 설정

### 필수 요구사항
- Java 21
- Gradle 8+
- PostgreSQL

### 로컬 개발 환경 설정
1. 프로젝트 클론
```bash
git clone [repository-url]
cd iitp-api
```

2. 환경 변수 설정
```bash
# Jasypt 암호화 키 설정 (선택사항)
export JASYPT_ENCRYPTOR_PASSWORD=your-password
```

3. 애플리케이션 실행
```bash
# 로컬 환경
./gradlew bootRun

# 또는 특정 환경 지정
./gradlew bootRun -Dspring.profiles.active=dev
```

## 빌드 및 배포

### 빌드
```bash
# 로컬 환경
./gradlew buildLocal

# 개발 환경
./gradlew buildDev

# 스테이징 환경
./gradlew buildStage

# 프로덕션 환경
./gradlew buildProd
```

### 빌드된 JAR 실행
```bash
# 로컬 환경
java -jar build/libs/iitp-api-0.0.1-local-{timestamp}.jar

# 개발 환경
java -jar build/libs/iitp-api-0.0.1-dev-{timestamp}.jar

# 스테이징 환경
java -jar build/libs/iitp-api-0.0.1-stage-{timestamp}.jar

# 프로덕션 환경
java -jar build/libs/iitp-api-0.0.1-prod-{timestamp}.jar
```

## 모니터링
- Actuator 엔드포인트: `/actuator`
- Prometheus 메트릭: `/actuator/prometheus`
- 주요 메트릭:
  - API 요청 수
  - 응답 시간
  - 에러율
  - Rate Limiting 상태

## 라이선스
이 프로젝트는 [라이선스 이름] 라이선스 하에 배포됩니다. 