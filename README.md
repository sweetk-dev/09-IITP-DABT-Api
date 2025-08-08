# 09-IITP-DABT-Api
9.모델 연동 API 모듈

## 프로젝트 개요
IITP API 서비스는 장애인 통계 및 POI 정보를 제공하는 RESTful API 서버입니다.

## 기술 스택 및 주요 플러그인

- Java 21
- Spring Boot 3.2.5
- Spring Security, Spring Data JPA, QueryDSL 5.0.0
- PostgreSQL (PostGIS 확장 지원)
- Bucket4j 8.7.0 (Rate Limiting)
- SpringDoc OpenAPI 2.3.0
- Micrometer (Prometheus)
- Gradle 8+
- Lombok

### 주요 Gradle 플러그인
- org.springframework.boot
- io.spring.dependency-management
- org.springdoc.openapi-gradle-plugin
- com.github.node-gradle.node

### 자주 사용하는 빌드/실행 옵션
- `-Dfile.encoding=UTF-8` (출력 인코딩)
- `--rerun-tasks` (태스크 강제 실행)
- `--stacktrace` (에러 상세 로그)

### 주요 환경 변수
- `SPRING_PROFILES_ACTIVE` (Spring 프로필)
- `JASYPT_ENCRYPTOR_PASSWORD` (암호화 키)

## 주요 기능
- 통계 데이터/POI API, API Key 인증, Rate Limiting, 표준화된 에러/응답, API 버전 관리
- 위치 기반 검색 및 거리 계산 (PostGIS/PostgreSQL 지원)

## 프로젝트 구조
### Java 소스
```
src/main/java/com/sweetk/iitp/api/
├── controller/  # API 컨트롤러
├── service/     # 비즈니스 로직
├── repository/  # 데이터 접근
├── entity/      # JPA 엔티티
├── config/      # 설정
├── annotation/           # 커스텀 어노테이션
├── constant/            # 상수 정의
├── dto/                 # Data Transfer Objects
├── exception/           # 예외 처리
├── interceptor/         # 인터셉터
├── security/            # 보안 관련 클래스
├── util/                # 유틸리티 클래스
└── validation/          # 검증 관련 클래스
```
### 리소스
```
src/main/resources/
├── application.yml, application-{env}.yml
├── log4j2-common.xml, log4j2-{env}.xml
```

---

## 개발 환경 준비
1. **필수 요구사항**: Java 21, Gradle 8+, PostgreSQL
2. **프로젝트 클론**
   ```bash
   git clone [repository-url]
   cd iitp-api
   ```
3. **DB/환경설정**: PostgreSQL 설치 및 환경별 yml 파일 확인

### 거리 계산 설정
프로젝트는 위치 기반 검색을 위한 거리 계산 방식을 설정으로 선택할 수 있습니다.

#### 설정 옵션 (application.yml)
```yaml
app:
  distance-calculation:
    # 거리 계산 방식
    method: POSTGIS_SPHERE  # POSTGIS_SPHERE, POSTGIS_PLANAR, POSTGRESQL_EARTH
```

#### 거리 계산 방식 비교

| 방식 | 함수 | 정확도 | 성능 | 요구사항 |
|------|------|--------|------|----------|
| **POSTGIS_SPHERE** | `ST_Distance_Sphere` | ⭐⭐⭐⭐⭐ | ⭐⭐⭐⭐ | PostGIS 확장 |
| **POSTGIS_PLANAR** | `ST_Distance` | ⭐⭐⭐ | ⭐⭐⭐⭐⭐ | PostGIS 확장 |
| **POSTGRESQL_EARTH** | `earth_distance` | ⭐⭐⭐⭐ | ⭐⭐⭐⭐ | PostgreSQL 기본 |

#### 권장 설정
- **개발 환경**: `POSTGRESQL_EARTH` (PostGIS 설치 없이 사용 가능)
- **운영 환경**: `POSTGIS_SPHERE` (가장 정확한 거리 계산)

#### PostGIS 확장 설치 (운영 환경)
```sql
-- PostgreSQL에서 PostGIS 확장 설치
CREATE EXTENSION postgis;
CREATE EXTENSION postgis_topology;
```

---

## 빌드 및 실행

### 1. 환경별 빌드

#### (1) Gradle 태스크 직접 사용
```bash
./gradlew buildLocal   # 로컬
./gradlew buildDev     # 개발
./gradlew buildStage   # 스테이지
./gradlew buildProd    # 운영
```
- 각 환경의 설정 파일(`application-{env}.yml`, `log4j2-{env}.xml`)만 JAR에 포함

#### (2) 빌드 자동화 스크립트
```bash
# Mac/Linux
./build.sh [local|dev|stage|prod]
# Windows
build.bat [local|dev|stage|prod]
```
- 옵션 생략 시 local
- OpenAPI 문서 생성/복사 + JAR 빌드 자동화

### 2. ## OpenAPI 문서 자동화 및 Stoplight Elements 그룹핑 안내

### 2.1. OpenAPI 문서 자동화
- 문서만 생성: `./gradlew generateOpenApiDocs`
- 문서 생성+복사: `./gradlew copyDocsToResources`
- build.sh/build.bat 사용 시 자동 포함
- 문서 위치: `docs-dist/`, `src/main/resources/static/docs/`

### 2.2. Stoplight Elements 그룹핑(x-tagGroups) 자동 추가
- Stoplight Elements에서 API 그룹핑을 위해 OpenAPI 문서의 root에 `x-tagGroups`가 필요합니다.
- 본 프로젝트는 빌드 후 파이썬 스크립트(`scripts/add-x-tagGroups.py`)로 자동 추가합니다.
- **최초 1회만 아래 명령어로 PyYAML 패키지를 설치하세요:**

```sh
pip install pyyaml
```
- 이후 `./gradlew copyDocsToResources` 또는 빌드 스크립트 실행 시 자동으로 그룹핑 정보가 추가됩니다.

### 3. 서버 실행

서버 실행 시 SPRING_PROFILES_ACTIVE 환경변수는 스크립트 내에서 고정되어 export됩니다.
- 실행 환경에 따라서 환경변수를 설정하세요. : 
  ```bash
  ./api_run.sh
  ```
- 필요시 실행 권한: `chmod +x api_run.sh`
- 또는 직접 실행:
  ```bash
  java -jar iitp-api.jar --spring.profiles.active=dev
  ```
- 다른 profile로 실행하려면 스크립트 내의 `export SPRING_PROFILES_ACTIVE=dev` 부분을 수정하세요.

---

## API 문서
- OpenAPI: `/v3/api-docs`, `/docs/latest.yaml`
- Stoplight Studio 등에서 yaml 파일 활용 가능

### 위치 기반 검색 API
프로젝트는 다음과 같은 위치 기반 검색 API를 제공합니다:

#### POI 검색 API
- **일반 검색**: `/api/v1/poi/search` - 카테고리, 키워드 기반 검색
- **위치 검색**: `/api/v1/poi/search/location` - 위도/경도 기반 반경 검색 (거리 정보 포함)
- **상세 조회**: `/api/v1/poi/{poiId}` - 개별 POI 상세 정보

#### 특화 POI API
- **지하철 엘리베이터**: `/api/v1/poi/subway-elevator/search`
- **무장애 관광지**: `/api/v1/poi/tour-bf-facility/search`
- **공중화장실**: `/api/v1/poi/public-toilet/search`

#### 거리 계산 방식
위치 검색 API는 설정에 따라 다음 방식으로 거리를 계산합니다:
- **PostGIS Sphere**: 지구 곡률을 고려한 정확한 구면 거리 (미터 단위)
- **PostGIS Planar**: 단순 유클리드 거리 (빠른 계산)
- **PostgreSQL Earth**: PostgreSQL 기본 함수 (확장 불필요)

---

## 모니터링
- Actuator: `/actuator`
- Prometheus: `/actuator/prometheus`
- 주요 메트릭: 요청 수, 응답 시간, 에러율, Rate Limiting

---

## 배포
- 빌드 후 deploy/ 디렉토리 구성 예시 및 환경별 설정 파일 위치 안내

---

## 라이선스
이 프로젝트는 [라이선스 이름] 하에 배포됩니다.

---

## 보안: Jasypt를 이용한 민감정보 암호화

운영 등 민감정보 보호가 필요한 환경에서는 Jasypt를 이용해 application.yml에 암호화된 값을 사용할 수 있습니다.

### 1. 암호화 방법 (Gradle 태스크)
```bash
./gradlew encrypt --plain-text 내비밀번호 --password 암호화키 -Dfile.encoding=UTF-8
```
- 한글 등 다국어 암호화 시 반드시 `-Dfile.encoding=UTF-8` 옵션을 추가하세요.
- 출력된 암호문을 `ENC(암호문)` 형태로 yml에 사용

### 2. application.yml 예시
```yml
spring:
  datasource:
    password: ENC(암호화된패스워드)
```

### 3. 복호화 환경변수
- 서버 실행 시 반드시 암호화키를 환경변수로 지정해야 합니다.
  - 환경변수: `JASYPT_ENCRYPTOR_PASSWORD=암호화키`
  - 또는 실행 옵션: `-Djasypt.encryptor.password=암호화키`

### 4. 참고
- Jasypt는 ENC()로 감싼 값만 복호화합니다. 평문은 그대로 사용됩니다.
- 암호화 알고리즘은 기본적으로 `PBEWithMD5AndDES`를 사용합니다.
- 암호화키는 외부에 노출되지 않도록 주의하세요.

### 4-1. 암호화 스크립트 사용법
- Mac/Linux: `./encrypt.sh 평문 암호화키`
- Windows: `encrypt.bat 평문 암호화키`
- 인코딩 옵션이 자동 적용되어 한글/다국어도 안전하게 암호화됩니다.

---

## 기타
- 문의/이슈는 [이슈 트래커/연락처]로

---