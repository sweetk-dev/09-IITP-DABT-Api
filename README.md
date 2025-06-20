# IITP API Service

## 프로젝트 개요
IITP API 서비스는 장애인 통계 및 POI 정보를 제공하는 RESTful API 서버입니다.

## 기술 스택 및 주요 플러그인

- Java 21
- Spring Boot 3.2.5
- Spring Security, Spring Data JPA, QueryDSL 5.0.0
- PostgreSQL
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

### 2. OpenAPI 문서 자동화
- 문서만 생성: `./gradlew generateOpenApiDocs`
- 문서 생성+복사: `./gradlew copyDocsToResources`
- build.sh/build.bat 사용 시 자동 포함
- 문서 위치: `docs-dist/`, `src/main/resources/static/docs/`

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

#### 표준 출력/에러 로그 관리 (logrotate)
- 표준 출력/에러 로그는 `logs/iitp-api-console.log`에 저장됩니다.
- 로그 파일이 커지지 않도록 logrotate 정책을 적용할 수 있습니다.
- 프로젝트 루트의 `iitp-api-console.log.logrotate` 파일을 `/etc/logrotate.d/`에 복사해 사용하세요.
- 정책: 월 1회 또는 50MB 초과 시 회전, 최대 6개 보관, 압축, 비어있으면 무시, 실행 중에도 안전하게 회전
- 예시:
  ```conf
  {fullpath:실제 프로젝트 경로}/logs/iitp-api-console.log {
      monthly
      size 50M
      rotate 6
      compress
      missingok
      notifempty
      copytruncate
  }
  ```
- **logrotate 적용 방법 참고:**
  1. 설정 파일 복사: (경로는 환경에 맞게 수정)
     ```bash
     sudo cp iitp-api-console.log.logrotate /etc/logrotate.d/iitp-api-console
     ```
  2. 설정 테스트:
     ```bash
     sudo logrotate -d /etc/logrotate.d/iitp-api-console
     ```
  3. 강제 회전 실행:
     ```bash
     sudo logrotate -f /etc/logrotate.d/iitp-api-console
     ```
  4. 시스템 전체 정책에 따라 자동으로 주기적 회전됨 (cron 등)

---

## API 문서
- OpenAPI: `/v3/api-docs`, `/docs/latest.yaml`
- Stoplight Studio 등에서 yaml 파일 활용 가능

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

### 1-1. 암호화 스크립트 사용법
- Mac/Linux: `./encrypt.sh 평문 암호화키`
- Windows: `encrypt.bat 평문 암호화키`
- 인코딩 옵션이 자동 적용되어 한글/다국어도 안전하게 암호화됩니다.

---

## 기타
- 문의/이슈는 [이슈 트래커/연락처]로