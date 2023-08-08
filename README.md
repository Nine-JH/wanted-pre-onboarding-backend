# wanted-pre-onboarding-backend (구자현)
원티드 프리온보딩 백엔드 인턴십 - 선발 과제입니다.
<br>
<br>

## 기능 구현

### 회원가입 & 로그인
![회원가입.gif](gif%2F%ED%9A%8C%EC%9B%90%EA%B0%80%EC%9E%85.gif)

### 게시글쓰기
![글쓰기.gif](gif%2F%EA%B8%80%EC%93%B0%EA%B8%B0.gif)

### 게시글 수정
![게시글 수정.gif](gif%2F%EA%B2%8C%EC%8B%9C%EA%B8%80%20%EC%88%98%EC%A0%95.gif)

### 게시글 삭제
![게시글 삭제.gif](gif%2F%EA%B2%8C%EC%8B%9C%EA%B8%80%20%EC%82%AD%EC%A0%9C.gif)

### 게시글 조회
![게시글 조회.gif](gif%2F%EA%B2%8C%EC%8B%9C%EA%B8%80%20%EC%A1%B0%ED%9A%8C.gif)

<br>
<br>


## 실행
### 준비물
* JDK 17 버전 이상
* Docker, Docker-compose 설치가 되어있어야 함.
<br>

### makefile command 사용
```cmd
$ pwd # **/wanted-pre-onboarding-backend 이 출력되어야 함.
$ make run-local
```
* `makefile`의 명령어를 실행합니다.
* 일반적인 설정을 하지 않고 실행하려면 `run-local`을 사용하면 됩니다.
<br>
<br>

## API 문서
기본적인 API 설계는 [ISSUE #3](https://github.com/Nine-JH/wanted-pre-onboarding-backend/issues/3)를 참고해 주세요.  
이후 프로젝트 코드와 통일성을 이루기 위해 `Spring Rest Docs`로 작성되었습니다.
* 확인 방법
  1. root context의 `api_문서.html` 또는 [위키](https://github.com/Nine-JH/wanted-pre-onboarding-backend/wiki/API-문서)참조
  2. 프로젝트 빌드 후 `http://${HOST}:${PORT}/docs/index.html`
<br>

## 설계
### DB
![image](https://github.com/Nine-JH/wanted-pre-onboarding-backend/assets/139187207/efff3459-4d4c-4ab4-92f6-d322812e9c33)

### 유스케이스 설계
![image](https://github.com/Nine-JH/wanted-pre-onboarding-backend/assets/139187207/d1758c35-2029-472c-8294-5cf2489bc2c5)
* [ISSUE #2](https://github.com/Nine-JH/wanted-pre-onboarding-backend/issues/2)


### JWT는 어떻게?
* [ISSUE #10](https://github.com/Nine-JH/wanted-pre-onboarding-backend/issues/10)

### Exception Handling
* [ISSUE #34](https://github.com/Nine-JH/wanted-pre-onboarding-backend/issues/38)
* [PR #34](https://github.com/Nine-JH/wanted-pre-onboarding-backend/pull/34)
<br>
<br>
<br>

## 환경설정
### 1) Docker-Compose 환경설정
Project Root에 존재하는 `.env`를 통해 실행 설정을 마쳐야합니다.

```env
# MYSQL
# 주의사항 : MYSQL_URL의 PORT는 항상 내부 컨테이너의 포트로 설정해주세요 (보통 3306)
# MYSQL_SCHEMA_NAME과 MYSQL_URL의 SCHEMA_NAME을 일치시켜주세요
MYSQL_CONTAINER_NAME=wanted_mysql
MYSQL_URL=jdbc:mysql://${MYSQL_CONTAINER_NAME_값}:3306/${SCHEMA_이름}?serverTimezone=UTC&characterEncoding=UTF-8
MYSQL_SCHEMA_NAME=wanted
MYSQL_USERNAME=root
MYSQL_PASSWORD=1234
MYSQL_VOLUME=

# REDIS

# 주의사항 : MYSQL과 동일하게 PORT는 항상 내부 컨테이너의 포트로 설정해주세요 (보통 6379)
REDIS_CONTAINER_NAME=wanted_redis
REDIS_HOST=redis
REDIS_PORT=6379
REDIS_VOLUME=

# EXPOSED PORT
SPRING_EXPOSE_PORT=8080
MYSQL_EXPOSE_PORT=3336
REDIS_EXPOSE_PORT=6379
```
<br>

### 2) DockerFile
```dockerfile
FROM azul/zulu-openjdk:19.0.2

WORKDIR /app

COPY build/libs/wanted-pre-onboarding-0.0.1-SNAPSHOT.jar /app/webApp.jar

ENTRYPOINT ["java", "-jar", "/app/webApp.jar"]
```
* 설정 변경이 필요한 경우 위의 파일을 수정하세요.
<br>

### 3) Docker-Compose 설정
```yaml
version: '3'
services:
  # MySQL 설정
  mysql-db:
    container_name: ${MYSQL_CONTAINER_NAME}
    image: mysql:8
    ports:
      - ${MYSQL_EXPOSE_PORT}:3306
    environment:
      MYSQL_DATABASE: ${MYSQL_SCHEMA_NAME}
      MYSQL_ROOT_PASSWORD: ${MYSQL_PASSWORD}
    volumes:
      - mysql-data:/var/lib/mysql
      - ./init.sql:/docker-entrypoint-initdb.d/init.sql
    networks:
      - wanted-network

  # Redis 설정
  redis:
    container_name: ${REDIS_CONTAINER_NAME}
    image: redis:7.2-rc2-alpine
    ports:
      - ${REDIS_EXPOSE_PORT}:6379
    volumes:
      - redis-data:/data
    networks:
      - wanted-network

  # Spring Boot Application 설정
  springboot-app:
    container_name: wanted_springboot_app
    build:
      context: .
      dockerfile: dockerfile
    ports:
      - ${SPRING_EXPOSE_PORT}:8080
    environment:
      SPRING_DATASOURCE_URL: ${MYSQL_URL}
      SPRING_DATASOURCE_USERNAME: ${MYSQL_USERNAME}
      SPRING_DATASOURCE_PASSWORD: ${MYSQL_PASSWORD}
      SPRING_DATA_REDIS_HOST: ${REDIS_CONTAINER_NAME}
      SPRING_DATA_REDIS_PORT: ${REDIS_PORT}
    depends_on:
      - mysql-db
      - redis
    networks:
      - wanted-network

volumes:
  mysql-data:
  redis-data:

networks:
  wanted-network:
```
* 기본적인 변수들은 모두 `.env`에서 가져옵니다. 추가적으로 설정할 곳은 volumes 인데 알아서 경로를 설정하시면 됩니다.
<br>

### 4) init.sql 설정
```sql
CREATE DATABASE IF NOT EXISTS wanted;
```
* Docker-Compose 에서 기본 SCHEMA 생성 실패 시를 대비해 ini.sql을 작동시킵니다.
* 관련 로직은 `docker-compose.yaml`의  `./init.sql:/docker-entrypoint-initdb.d/init.sql` 을 참고하세요
  <br>

<br>
<br>
     
## Test
### 1) Unit, Integeration
기본적으로 다음 기준으로 테스트코드 작성을 실시했습니다.
* Domain, Controller Layer -> Mockist 유닛테스트
* Repository, Application Layer -> Classicist 유닛 테스트 (거의 Integration으로 간주해도 될 것 같습니다.)  
<br>

### 2) TestContainers
* 실제와 가까운 테스트를 하고 싶었지만, 이 경우에는 개발자들이 각각 로컬 환경 설정을 해야하는 불편함이 있습니다.  이 때문에 `TestContainers`를 도입해보았습니다.

#### TestContainer 설정 경로
* [test-compose.yaml](https://github.com/Nine-JH/wanted-pre-onboarding-backend/blob/main/src/test/resources/compose-test.yaml)
* [IntegrationTestSupporter.java](https://github.com/Nine-JH/wanted-pre-onboarding-backend/blob/main/src/test/java/me/jh9/wantedpreonboarding/utils/IntegrationTestSupport.java)

#### 단점
* 컨테이너를 빌드하기 때문에 테스트 속도가 비교적 느려집니다.
* 현재는 Class.Instance 마다 컨테이너를 다시 만들기 때문에 **컨테이너 재사용 설정**을 켜 속도를 개선할 수 있습니다.
<br>

### 3) Jacoco
<img width="1213" alt="image" src="https://github.com/Nine-JH/wanted-pre-onboarding-backend/assets/139187207/608d3369-950a-4bdd-beed-4f32c0471c5e">
