# IITP Open API 문서 제공 서버 세팅 절차

- **버전**: v0.0.2
- **최종 수정일**: 2025-10-21
- **작성일**: 2025-07-15



---



## 목차

1. 문서 히스토리
2. 개요
3. 사전 준비 사항
4. Stoplight Elements 설치 및 설정
5. OpenAPI 문서 배포
6. Prism Mock 서버 설정
7. Nginx 연동 설정
8. 인증 및 보안 설정 (선택)
9. 부록 (사전 준비 설치 가이드 및 참고 자료)
<div style="page-break-after: always;"></div>


---


## 1. 문서 히스토리

| 버전    | 일자         | 변경 내용                                               |
| ----- | ---------- | --------------------------------------------------- |
| 0.0.2 | 2025-10-21 | Prism `--dynamic` 옵션 추가, nginx 설정 개선, 트러블슈팅 추가 |
| 0.0.1 | 2025-07-15 | 최초 작성 및 전체 구축 절차 반영                                |

<div style="page-break-after: always;"></div>

---


## 2. 개요

이 문서는 IITP Open API 문서 제공 및 테스트를 위한 서버를 구성하는 전체 절차를 정리한 문서입니다. 실제 운영 환경 또는 개발 테스트 환경에서 API 문서를 시각화하고, Mock API 테스트까지 가능하도록 설정하는 것이 목적입니다.

주요 구성 요소:

- Stoplight Elements (OpenAPI 문서 렌더링)
- Prism Mock Server (OpenAPI 기반 테스트용 Mock API)
- Nginx (정적 문서 및 포워딩 서버)

## 3. 사전 준비 사항

- Ubuntu 22.04.5 LTS 이상 기반 리눅스 서버
- Node.js (권장 버전: 18.x 이상) 및 npm
- NVM 설치 권장 (`nvm install 18 && nvm use 18`)
- Nginx 설치

## 4. Stoplight Elements 설치 및 설정
※ CDN 방식이므로 npm install은 필요하지 않음

1. 문서 디렉토리 구성:

```bash
sudo mkdir -p /var/www/html/docs
mkdir -p ~/openapi-docs/docs
cd ~/openapi-docs/docs
```

2. 문서 작성 또는 복사:
- 서버에 적용할 OpenAPI 문서 업로드 
   - 대상 파일 : latest.yaml, openapi-vX.X.X.yaml

```bash
sudo cp -p ~/openapi-docs/docs/*.yaml /var/www/html/docs/
```

3. index.html 생성:

```html
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>IITP OpenAPI 문서</title>
      <link rel="icon" href="/favicon.ico" sizes="any">
      <link rel="stylesheet" href="https://unpkg.com/@stoplight/elements/styles.min.css">
    <script type="module">
      import 'https://unpkg.com/@stoplight/elements/web-components.min.js';

      window.addEventListener('DOMContentLoaded', () => {
        const spec = new URLSearchParams(window.location.search).get('spec') || '/docs/latest.yaml';

        const el = document.createElement('elements-api');
        el.apiDescriptionUrl = spec;
        el.layout = 'sidebar';
        el.router = 'hash';
        el.hideTryIt = false;
        el.hideExport = true;

        document.body.appendChild(el);
      });
    </script>
  </head>
  <body></body>
</html>
```

4. 실제 서비스 디렉토리에 복사:

```bash
sudo mkdir -p /var/www/html/docs
sudo cp -r ~/openapi-docs/docs/* /var/www/html/docs/
```

## 5. OpenAPI 문서 배포
### 5.1 초기 배포 설정
Nginx를 통해 `/docs/latest.yaml` 경로로 접근 가능하도록 설정 (Nginx 연동 설치 참고)

### 5.2 OpenAPI 재 배포

- 서버에 적용할 OpenAPI 문서 업로드 후 prism, nginx 재 기동 
  - 대상 파일 : latest.yaml, openapi-vX.X.X.yaml
  - 서버 업로드 dir :  ~/openapi-docs/docs
  
```bash
# OpenAPI 문서 파일 복사
sudo cp -p ~/openapi-docs/docs/*.yaml /var/www/html/docs/

# Prism Mock 서버 재시작 (문서 변경 반영)
sudo systemctl restart prism.service

# Nginx 리로드 (설정 변경 시만 필요)
sudo systemctl reload nginx
```

**참고:** 
- OpenAPI 문서만 변경한 경우: Prism 재시작만 필요
- Prism 서비스 파일 수정 시: `sudo systemctl daemon-reload` 먼저 실행 후 재시작


## 6. Prism Mock 서버 설정

### 6.1 prism-cli 설치

```bash
# nvm으로 설치한 Node는 sudo 없이도 설치 가능
npm install -g @stoplight/prism-cli
```

※ 권장: nvm을 이용해 Node 18+ 버전 사용

설치 확인:

```bash
which prism
prism --version
```

### 6.2 systemd 서비스 등록

- prim 설치 경로 확인 후 수정 (/usr/local/bin/prism)
- user, group 은 실제 실행 계정 권환 확인 필요 
`/etc/systemd/system/prism.service` 파일 생성:

```ini
[Unit]
Description=Prism Mock Server
After=network.target

[Service]
ExecStart=/usr/local/bin/prism mock /var/www/html/docs/latest.yaml -p 4010 --dynamic
Restart=always
RestartSec=3
User=sweetk
Group=sweetk
Environment=PATH=/usr/local/bin:/usr/bin:/bin
Environment=NODE_ENV=production
WorkingDirectory=/var/www/html/docs
Type=simple

[Install]
WantedBy=multi-user.target
```

**주요 옵션:**
- `-p 4010`: 포트 지정
- `--dynamic`: 스키마 기반 동적 mock 데이터 자동 생성 (권장)

**중요:** 
- Mock 서버도 실제 API처럼 `X-API-KEY` 헤더를 필수로 요구합니다
- 개발자들이 인증 헤더의 중요성을 인식하도록 의도적으로 검증합니다
- 헤더가 없으면 `401 Unauthorized` 또는 `407` 에러가 발생합니다

설정 적용 및 실행:

```bash
# systemd 데몬 리로드 (서비스 파일 변경 시 필수!)
sudo systemctl daemon-reload

# 서비스 등록 및 자동 시작 활성화
sudo systemctl enable prism.service

# 서비스 시작
sudo systemctl start prism.service

# 상태 확인
sudo systemctl status prism.service
```

**중요:** 서비스 파일(`/etc/systemd/system/prism.service`)을 수정한 후에는 **반드시 `daemon-reload`를 먼저 실행**해야 변경사항이 적용됩니다.

정상 실행 확인:

```bash
# X-API-KEY 헤더 포함 (필수!)
curl -H "X-API-KEY: test" "http://localhost:4010/api/v1/poi/search?page=1&size=10"

# 헤더 없이 호출하면 401/407 에러 발생 (의도된 동작)
curl "http://localhost:4010/api/v1/poi/search?page=1&size=10"
```

**참고:** Mock 서버는 헤더가 존재하는지만 확인하며, 실제 값은 검증하지 않습니다.

## 7. Nginx 연동 설정

Nginx 설정 파일: `/etc/nginx/conf.d/docs.conf`

### 7.1 기본 설정 (80 포트)

```nginx
server {
    listen 80 default_server;
    listen [::]:80 default_server;
    server_name _;

    root /var/www/html;
    index index.html;

    # 정적 문서 제공
    location /docs/ {
        index index.html;
        try_files $uri $uri/ =404;
    }

    # Mock API - /mock/ 경로로 접근
    location /mock/ {
        proxy_pass http://localhost:4010/;  # 끝에 / 필수! (경로 제거)
        proxy_http_version 1.1;
        proxy_set_header Upgrade $http_upgrade;
        proxy_set_header Connection keep-alive;
        proxy_set_header Host $host;
        proxy_cache_bypass $http_upgrade;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
    }
}
```

**중요:** `proxy_pass http://localhost:4010/` 끝에 `/`가 있어야 `/mock/` 경로가 제거됩니다.

### 7.2 추가 포트 설정 (선택사항)

외부에서 별도 포트(예: 24010)로 직접 접근하려면 같은 server 블록에 추가:

```nginx
server {
    listen 80 default_server;
    listen [::]:80 default_server;
    listen 24010;           # Mock 전용 포트 추가
    listen [::]:24010;      # IPv6
    server_name _;

    root /var/www/html;

    # ... 기존 location 설정 ...
}
```

설정 적용:

```bash
sudo nginx -t
sudo systemctl reload nginx
```

### 7.3 접속 예시

**OpenAPI 문서 보기:**
```
http://192.168.60.142/docs/?spec=/docs/latest.yaml
```

**Mock API 호출:**
```bash
# 80 포트 + /mock/ 경로 (X-API-KEY 헤더 필수!)
curl -H "X-API-KEY: test" http://192.168.60.142/mock/api/v1/emp/workplace/standard

# 24010 포트 직접 접근 (추가 포트 설정 시)
curl -H "X-API-KEY: test" http://192.168.60.142:24010/api/v1/emp/workplace/standard

# 헤더 없이 호출 시 인증 에러 발생 (의도된 동작)
curl http://192.168.60.142/mock/api/v1/emp/workplace/standard
# → 401 Unauthorized 또는 407 에러
```

### 7.4 트러블슈팅

**502 Bad Gateway 에러:**
- Prism이 `localhost:4010`에서 정상 동작 중인지 확인
- `sudo systemctl status prism.service` 로 상태 확인

**404 Not Found 에러 (Route not matched):**
- nginx `proxy_pass` 끝에 `/` 있는지 확인
- `/mock/` 경로가 제거되어야 Prism이 정상 처리

**복잡한 객체 직렬화 에러:**
- Prism 서비스에 `--dynamic` 옵션 추가 (권장)

**401/407 인증 에러:**
```json
{
  "status": "401 UNAUTHORIZED" 또는 "407 PROXY_AUTHENTICATION_REQUIRED",
  "success": false,
  "error": { ... }
}
```
- **의도된 동작:** Mock 서버도 인증 헤더를 필수로 요구합니다
- **해결:** 요청 시 `-H "X-API-KEY: test"` 헤더 추가
- **목적:** 개발자들이 API 인증의 중요성을 Mock 단계부터 인식하도록 함
- **참고:** Prism은 헤더 존재 여부만 확인하며, 실제 값은 검증하지 않습니다

**systemd 경고 (changed on disk):**
```
Warning: The unit file, source configuration file or drop-ins of prism.service changed on disk.
Run 'systemctl daemon-reload' to reload units.
```
- 원인: 서비스 파일 수정 후 `daemon-reload` 미실행
- 해결: `sudo systemctl daemon-reload` 실행 후 재시작

## 8. 인증 및 보안 설정 (선택)

- JWT, API Key 등 설정에 따라 프록시 인증 적용 가능

## 9. 부록 (사전 준비 설치 가이드 및 참고 자료)
### A. Node.js & NVM 설치

```bash

# nvm 설치 (만약 안되어 있다면)
curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.39.7/install.sh | bash
source ~/.bashrc  # 또는 source ~/.zshrc

# 최신 LTS 버전 설치 및 사용
nvm install --lts
nvm use --lts

# 기본값 설정
nvm alias default lts/*

#설치된 node.js 버전 확인  
node -v
```

### B. Nginx 설치

```bash
sudo apt update
sudo apt install nginx -y
```

---

