카카오페이뿌리기 기능 구현하기 API 개발
## 개발 프레임워크
* Language : Java SE 8
* Framework: Spring Boot 2.2.2
* Database: H2database

## 문제 해결 방법
### 데이터
##### THROW_REQUEST (뿌리기 요청)
|Column|Type|Description|
|---|----|----|
|userId|Long|뿌린사용자아이디|
|roomId|String|뿌린대화방아이디|
|token|String|뿌리기요청고유토큰|
|throwMoney|int|뿌릴금액|
|throwNum|int|뿌릴인원|
|throwDate|LocalDateTime|뿌리기요청일자|
|createdDate|LocalDateTime|생성일자|
|modifiedDate|LocalDateTime|수정일자|

##### THROW_MONEY_RECEIVE (뿌린 돈 받기)
|Column|Type|Description|
|---|----|----|
|userId|Long|뿌린사용자아이디|
|roomId|String|뿌린대화방아이디|
|token|String|뿌리기요청고유토큰|
|orderNum|int|뿌린돈 분배 번호|
|dividend|int|뿌린돈 분배 금액|
|receiveUserId|Long|받은사용자아이디|
|receiveDate|LocalDateTime|뿌리기받은일자|
|createdDate|LocalDateTime|생성일자|
|modifiedDate|LocalDateTime|수정일자|

##### USER_INFO (사용자 정보)
|Column|Type|Description|
|---|----|----|
|userId|Long|사용자아이디|
|balance|int|잔액|
|createdDate|LocalDateTime|생성일자|
|modifiedDate|LocalDateTime|수정일자|

##### ROOM_INFO (대화방 정보)
|Column|Type|Description|
|---|----|----|
|Id|Long|아이디|
|userId|Long|사용자아이디|
|roomId|String|대화방아이디|
|createdDate|LocalDateTime|생성일자|
|modifiedDate|LocalDateTime|수정일자|

#### ROOM_INFO - Init Data(init.csv)
bd839349-a6f5-4b90-9790-76f90c264c26,1000000001
bd839349-a6f5-4b90-9790-76f90c264c26,1000000002
bd839349-a6f5-4b90-9790-76f90c264c26,1000000003
bd839349-a6f5-4b90-9790-76f90c264c26,1000000004
8d8734ba-7196-4074-bd1f-f0deef11dc38,1000000005
8d8734ba-7196-4074-bd1f-f0deef11dc38,1000000006
8d8734ba-7196-4074-bd1f-f0deef11dc38,1000000007
8d8734ba-7196-4074-bd1f-f0deef11dc38,1000000008
8d8734ba-7196-4074-bd1f-f0deef11dc38,1000000009
8d8734ba-7196-4074-bd1f-f0deef11dc38,1000000010
a0b8ec1f-f079-4e73-89c9-aa64930fe0ab,1000000001
a0b8ec1f-f079-4e73-89c9-aa64930fe0ab,1000000002
a0b8ec1f-f079-4e73-89c9-aa64930fe0ab,1000000005
a0b8ec1f-f079-4e73-89c9-aa64930fe0ab,1000000006
fc344fb1-1bea-40f4-88fd-968d2bec971d,1000000001
fc344fb1-1bea-40f4-88fd-968d2bec971d,1000000002
fc344fb1-1bea-40f4-88fd-968d2bec971d,1000000003
fc344fb1-1bea-40f4-88fd-968d2bec971d,1000000004
fc344fb1-1bea-40f4-88fd-968d2bec971d,1000000005
fc344fb1-1bea-40f4-88fd-968d2bec971d,1000000006
fc344fb1-1bea-40f4-88fd-968d2bec971d,1000000007
fc344fb1-1bea-40f4-88fd-968d2bec971d,1000000008
fc344fb1-1bea-40f4-88fd-968d2bec971d,1000000009
fc344fb1-1bea-40f4-88fd-968d2bec971d,1000000010
c0cb235f-dc6b-4590-a739-23ec4ff3324f,1000000003
c0cb235f-dc6b-4590-a739-23ec4ff3324f,1000000004
c0cb235f-dc6b-4590-a739-23ec4ff3324f,1000000007
c0cb235f-dc6b-4590-a739-23ec4ff3324f,1000000008


### API 기능 명세
|Method|URL|Description|
|---|----|----|
|POST|/api/throw|뿌리기API |
|POST|/api/receive|받기API|
|GET|/api/throw|조회 API|


### API 상세 설명
- 뿌리기 API
>- POST /api/throw
>- 리퀘스트헤더에 사용자 아이디 대화방아이디, 리퀘스트바디에 뿌릴금액, 뿌릴인원 수를 담아 요청합니다.
>- Request Header 
```
{
	“X-USER-ID”: 1000000001,
	“X-ROOM-ID”: “fc344fb1-1bea-40f4-88fd-968d2bec971d”
}
>- Request Body 
```
{
	"throwMoney":10000,
	"throwNum": 7
}
```
>- 응답은 토큰 값만 내려줍니다.
>- Response Body
```
{
    "token": "dHh"
}
```

- 받기 API
>- POST /api/receive
>- 리퀘스트헤더에 받을 사용자 아이디 대화방아이디, 리퀘스트바디에 토큰을 담아 요청합니다.
>- Request Header 
```
{
	“X-USER-ID”: 1000000002,
	“X-ROOM-ID”: “fc344fb1-1bea-40f4-88fd-968d2bec971d”
}
>- Request Body 
```
{
	"token": "dHh"
}
```
>- 응답은 받은 금액만 내려줍니다.
>- Response Body
```
{
    "dividend": 4454
}
```

- 조회 API
>- GET /api/throw?token=dHh
>- 리퀘스트헤더에 받을 사용자 아이디 대화방아이디, 쿼리스트링에 토큰을 담아 요청합니다.
>- Request Header 
```
{
	“X-USER-ID”: 1000000002,
	“X-ROOM-ID”: “fc344fb1-1bea-40f4-88fd-968d2bec971d”
}
>- 응답은 뿌린시각, 뿌린금액, 받기완료된금액, 받기완료된정보(금액,사용자)리스트를 내려줍니다.
>- Response Body
```
{
    "throwDate": {
        "dayOfMonth": 27,
        "dayOfWeek": "SATURDAY",
        "dayOfYear": 179,
        "month": "JUNE",
        "year": 2020,
        "hour": 8,
        "minute": 44,
        "monthValue": 6,
        "nano": 47000000,
        "second": 39,
        "chronology": {
            "id": "ISO",
            "calendarType": "iso8601"
        }
    },
    "throwMoney": 10000,
    "receiveFinishMoney": 10000,
    "receiveFinishInfoList": [
        {
            "dividend": 1904,
            "receiveUserId": 1000000003
        },
        {
            "dividend": 615,
            "receiveUserId": 1000000004
        },
        {
            "dividend": 4454,
            "receiveUserId": 1000000002
        },
        {
            "dividend": 7,
            "receiveUserId": 1000000006
        },
        {
            "dividend": 19,
            "receiveUserId": 1000000007
        },
        {
            "dividend": 1000,
            "receiveUserId": 1000000005
        },
        {
            "dividend": 2001,
            "receiveUserId": 1000000008
        }
    ]
}
```
 
## 빌드 및 실행 방법
* 이클립스에서 GIT으로 프로젝트를 Import를 한다.
* 프로젝트 우클릭 Gradle -> Refresh Gradle Project를 한다.
* Boot Dashboad 에서 해당 프로젝트를 Start를 한다.  
