# IITP Open API 문서 제공 서버 세팅 절차

- **버전**: v0.0.1
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

| 버전    | 일자         | 변경 내용               |
| ----- | ---------- | ------------------- |
| 0.0.1 | 2025-07-15 | 최초 작성 및 전체 구축 절차 반영 |

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
sudo cp -p ~/openapi-docs/docs/*.yaml /var/www/html/docs/
sudo systemctl restart prism.service
sudo systemctl reload nginx
```


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
ExecStart=/usr/local/bin/prism mock /var/www/html/docs/latest.yaml -p 4010
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

설정 적용 및 실행:

```bash
sudo systemctl daemon-reexec
sudo systemctl daemon-reload
sudo systemctl enable --now prism.service
sudo systemctl start prism.service
sudo systemctl status prism.service
```

정상 실행 확인:

```bash
curl "http://localhost:4010/api/v1/poi/search?page=1&size=10"
```

→ 결과가 `401 Unauthorized` 라면, spec에서 `security` 설정 제거로 bypass 가능

## 7. Nginx 연동 설정

Nginx 설정 파일: `/etc/nginx/conf.d/docs.conf`

- location /mock 에서 domain 또는 실제 서버 IP 로 변경
```nginx
server {
    listen 80;
    server_name _;

    root /var/www/html;

    location /docs/ {
        index index.html;
        try_files $uri $uri/ =404;
    }

    location /mock/ {
        proxy_pass http://localhost:4010;
        proxy_http_version 1.1;
        proxy_set_header Upgrade $http_upgrade;
        proxy_set_header Connection keep-alive;
        proxy_set_header Host $host;
        proxy_cache_bypass $http_upgrade;
    }
}
```

설정 적용:

```bash
sudo nginx -t
sudo systemctl reload nginx
```

접속 예시:

- 문서: `http://your-ip/docs/?spec=/docs/latest.yaml`
- Mock: `http://your-ip/mock/api/v1/poi/search?page=1&size=10`

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

