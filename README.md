### Kusinsa
---
### 프로젝트 개요
Kusinsa는 e-commerce 서비스로, `무신사`를 벤치마킹했습니다.
---
### 사용 기술 스택
- Java8
- Spring Boot 2.7
- Spring Data JPA, QueryDSL
- Redis
- MySQL 8.0
- MongoDB 4.4.5
- Docker
- Github Actions


### 프로젝트목표
- 이벤트를 활용한 서비스를 만들기
- e커머스에서 발생하는 비즈니스로직의 동시성을 처리해보기
- 데이터 저장소들의 장점을 이용하여 문제를 해결해보기
- 하드웨어적으로 성능을 개선시키는게아니라 소프트웨어적으로 성능을 개선시켜보는것이 목표입니다.
- 이전까지 모두 팀원들과 함께한 프로젝트였지만 혼자 프로젝트를 진행하여 스스로 고민을 하여 해결
- 통계데이터 수집

### 🌈기능


### ✅ 회원가입, 로그인
- Kusinsa의 서비스 이용을 위해 회원가입 기능을 제공합니다.
- 로그인
- 로그아웃 기능을 제공합니다.

### ✅ 상품
- 상품은 조회/등록/수정/삭제 가 가능합니다.
- 상품은 카테고리, 브랜드 별로 조회도 가능합니다.
- 상품을 찜할수있습니다. (찜목록을 따로 볼 수 있습니다)
- 인기상품 목록을 볼 수 있습니다. - 랭킹 Top10 (판매, 조회)
- 최근본 상품을 조회할 수 있습니다
- 좋아요한 상품 목록을 볼 수 있습니다 

### ✅ 주문 
- 여러개의 상품 주문이 가능합니다
- 주문은 배송시작전 부분 취소가 가능합니다 
- 주문한 상품목록을 볼 수 있습니다

### ✅ 포인트
- 유저는 로그인시 하루에 한번 포인트 획득을 할 수 있습니다.
- 유저는 상품구매시 포인트 획득을 할 수 있습니다
- 유저는 획득하고 사용한 포인트 내역을 조회 할 수 있습니다

### ✅ 알림
- 주문 완료시 유저는 알림받을수있습니다
- 배송 상태 변경시 알림을 받을 수 있습니다
- 상품 재입고시 알림을 받을수있습니다 (그룹메시지)


### 프로젝트 주요 이슈해결사항
[이벤트를 활용해 책임 및 트랜잭션  분리](https://transparent-bugle-0d4.notion.site/888d0c0adfd94e01b4bb3e3df56f7466)
[커버링 인덱스를 사용하여 페이징 성능 개선기](https://transparent-bugle-0d4.notion.site/ebb08290afeb4efb8205e426b3a3758c)
[SortedSet을통한 job queue 구축 스케줄링](https://transparent-bugle-0d4.notion.site/SortedSet-job-queue-5446b0befa6e479d9e3c494038ea723d)
[재입고 알림 신청 동시성](https://transparent-bugle-0d4.notion.site/cc8078be34554524a9d656eac713d711)
[MongoDB 인덱스를 활용한 알림 메시지 설계](https://transparent-bugle-0d4.notion.site/MongoDB-7bb8938c1e5a4454b3034080ddd857b8)
[좋아요, 최근 본 상품](https://transparent-bugle-0d4.notion.site/0e8dfd0247d94b9baaee865df8e28699)
[랭킹TOP 10 서비스](https://transparent-bugle-0d4.notion.site/TOP-10-3df77822f56a4124aa52a2d36be9fe61)


### ERD
[ERD사진 링크](https://github.com/wangKurkus-develop/kusinsa/wiki/ERD)


### API - Postman
[API 문서 링크](https://documenter.getpostman.com/view/21534834/2s93CHtuM5#a4d7a6cc-2dd5-4e97-91ce-e0ccc33b229f)


### Installation
1. git clone
2. application.properties 파일의 profile을 dev로 바꿔주세요
3. mysql 8.0 버전에서 kusinsa db를 만들어주세요
3. resources/db에서 `V7_DB_table.sql` 스크립트를 실행시켜주세요
4. `docker-compose up -d` 명령어를 통해 redis를 실행시켜주세요
5. mongoDB 4.4 버전에서 kusinsa DB를 생성해주세요
6. 다음 명령어를 통해 컬렉션을 생성해주세요 `db.createCollection("click_ranks"); db.createCollection("notification_message"); db.createCollection("order_ransk");`
7. 실행시켜주세요
8. Postman을 다운받아주세요
9. [API 문서 링크](https://documenter.getpostman.com/view/21534834/2s93CHtuM5#a4d7a6cc-2dd5-4e97-91ce-e0ccc33b229f)를 통해 요청해보세요


### 아키텍쳐
![image](https://user-images.githubusercontent.com/79621675/221783055-f7543182-869f-4fbe-b663-5f6a0fb0e8ff.png)

