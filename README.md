# IITP API Service

## 프로젝트 개요
IITP API 서비스는 장애인 관련 통계 데이터와 POI(관심 지점) 정보를 제공하는 RESTful API 서버입니다. 이 서비스는 API Key 기반의 인증을 사용하며, Rate Limiting을 통해 API 사용량을 제어합니다.

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

### 통계 데이터 API
- 장애인 등록 현황 통계
- 연령별, 장애등급별, 성별 통계
- 시도별 통계
- 일상생활 지원 현황 통계
- 연도별 데이터 조회
- 통계 정보 메타데이터 제공

### POI(관심 지점) API
- 카테고리 기반 POI 검색
- 위치 기반 POI 검색
- 반경 검색 지원
- 페이징 및 정렬 기능
- 상세 정보 조회

### 공통 기능
- API Key 기반 인증
- Rate Limiting
- 요청 로깅
- 에러 처리 및 응답 표준화
- API 버전 관리

## API 엔드포인트

### 통계 데이터 API
- GET /api/v1/basic/housing/reg/new/* - 신규등록 장애인현황
- GET /api/v1/basic/housing/reg/ageSevGen/* - 연령별,장애등급별,성별 통계
- GET /api/v1/basic/housing/reg/sidoSevGen/* - 시도별 통계
- GET /api/v1/basic/housing/life/* - 일상생활 지원 현황

### POI API
- GET /api/v1/poi/search - POI 검색
- GET /api/v1/poi/search/location - 위치 기반 POI 검색

### 공통 기능
- 페이징: `page`, `size` 파라미터
- 정렬: `sort` 파라미터
- 연도 필터링: `fromYear` 파라미터
- 검색: `name` 파라미터

## 응답 형식
```json
{
    "code": "200",
    "message": "success",
    "data": {
        // 응답 데이터
    }
}
```

## 에러 응답
```json
{
    "code": "500",
    "message": "Internal Server Error",
    "data": null
}
```

## 프로젝트 구조
```
src/main/java/com/sweetk/iitp/api/
├── annotation/           # 커스텀 어노테이션
├── config/              # 설정 클래스
├── constant/            # 상수 정의
├── controller/          # API 컨트롤러
│   ├── v1/
│   │   ├── basic/      # 통계 데이터 API
│   │   └── poi/        # POI API
├── dto/                 # Data Transfer Objects
├── entity/              # JPA 엔티티
├── exception/           # 예외 처리
├── interceptor/         # 인터셉터
├── repository/          # 데이터 접근 계층
├── security/            # 보안 관련 클래스
├── service/             # 비즈니스 로직
├── util/                # 유틸리티 클래스
└── validation/          # 검증 관련 클래스
```

## 보안
- API Key 인증: 모든 API 요청은 유효한 API Key가 필요합니다.
- Rate Limiting: IP 기반으로 API 요청 횟수를 제한합니다.
- HTTPS: 모든 통신은 HTTPS를 통해 암호화됩니다.

## API 문서
- OpenAPI 문서: `/v3/api-docs`
- API 문서 파일: `/docs/latest.yaml`
- 상세 API 문서:
  - `/docs/api/basic-api.md` - 통계 데이터 API 문서
  - `/docs/api/poi-api.md` - POI API 문서

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

## 프로필 설정

### 로컬 개발 환경
```bash
# 커맨드 라인으로 프로필 지정
java -jar iitp-api.jar --spring.profiles.active=local
```

### 개발/스테이지/운영 환경
서버에 환경 변수로 설정 후 실행

```bash
# Linux/Mac
export SPRING_PROFILES_ACTIVE=dev  # 또는 stage, prod
java -jar iitp-api.jar

# Windows
set SPRING_PROFILES_ACTIVE=dev  # 또는 stage, prod
java -jar iitp-api.jar
```

## 빌드 및 실행

### 빌드
```bash
# 환경별 빌드 (각 환경에 맞는 설정 파일만 포함)
./gradlew buildLocal  # 로컬 환경
./gradlew buildDev    # 개발 환경
./gradlew buildStage  # 스테이지 환경
./gradlew buildProd   # 운영 환경
```

> **참고**: 각 환경별 빌드는 해당 환경의 설정 파일만 jar에 포함됩니다.
> - `application-{env}.yml`
> - `log4j2-{env}.xml`

### 실행
```bash
# Linux/Mac
./run.sh

# Windows
run.bat
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

## 프로젝트 구조
```
src/main/resources/
├── application.yml              # 기본 설정
├── application-local.yml        # 로컬 환경 설정
├── application-dev.yml          # 개발 환경 설정
├── application-stage.yml        # 스테이지 환경 설정
├── application-prod.yml         # 운영 환경 설정
├── log4j2-common.xml           # 로그 공통 설정
├── log4j2-local.xml            # 로컬 환경 로그 설정
├── log4j2-dev.xml              # 개발 환경 로그 설정
├── log4j2-stage.xml            # 스테이지 환경 로그 설정
└── log4j2-prod.xml             # 운영 환경 로그 설정
```

## 로그 설정
- 공통 로그 설정은 `log4j2-common.xml`에 정의
- 환경별 로그 설정은 각각의 `log4j2-{env}.xml`에 정의
- 로그 파일은 `logs` 디렉토리에 생성됨
  - 로컬: `iitp-api.log`
  - 개발: `iitp-api-dev.log`
  - 스테이지: `iitp-api-stage.log`
  - 운영: `iitp-api-prod.log`

## 개발 환경 설정
1. Java 17 설치
2. PostgreSQL 설치 및 데이터베이스 생성
3. 환경별 설정 파일 확인
   - `application-{env}.yml`
   - `log4j2-{env}.xml`

## 배포
1. 빌드
```bash
./gradlew build
```

2. 배포 파일 구성
```
deploy/
├── iitp-api.jar
└── config/
    ├── application-local.yml
    ├── application-dev.yml
    ├── application-stage.yml
    └── application-prod.yml
```

3. 실행
```bash
# 로컬
java -jar iitp-api.jar --spring.profiles.active=local

# 다른 환경
java -jar iitp-api.jar
``` 