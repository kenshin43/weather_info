DESC PERSON;
DESC COORDINATES;
DESC INTERESTAREA;
DESC WEATHER_INFO;
DESC CATEGORY;
DESC PRODUCT;
DESC SELL;
DROP TABLE PERSON;
DROP TABLE INTERESTAREA;
DROP TABLE COORDINATES;
DROP TABLE INTERESTAREA;

CREATE TABLE PERSON (
ID          VARCHAR2(40)    primary key,
PW          VARCHAR2(40)    not null,
NAME        VARCHAR2(20)    not null,
ADDR        VARCHAR2(100),
isADMIN     NUMBER         CHECK(isADMIN in (0, 1)));

CREATE TABLE COORDINATES (
LOCAL_CODE  NUMBER(15)      PRIMARY KEY,
LOCAL_NAME VARCHAR2(30),
PARENT_CODE NUMBER(15),
PARENT_NAME VARCHAR2(30),
X       NUMBER(4),
Y       NUMBER(4)
);

CREATE TABLE INTERESTAREA (
ID      VARCHAR2(40)        REFERENCES PERSON(ID),
LOCAL_CODE NUMBER(15)       REFERENCES COORDINATES(LOCAL_CODE)
);

CREATE TABLE WEATHER_INFO (
    X NUMBER(4)     , --그리드 x좌표
    Y NUMBER(4)     , --그리드 y좌표
    "HOUR" NUMBER(2), -- 동네예보 3시간 단위( 3, 6, 9, 12, 15, 18, 21, 24 ..)
    data_seq NUMBER(2), --시간 순서
    "DAY" NUMBER(1), -- (오늘:0/내일:1/모레:2 표시)
    TEMP NUMBER(4,1), --현재 시간 온도 (hour가 18이라면 15~18시 온도)
    TMX NUMBER(4,1), --일최고기온
    TMN NUMBER(4,1), --일최저기온
    SKY NUMBER(1), --하늘상태 (코드값) 1:맑음, 2:구름조금, 3:구름많음, 4:흐림
    PTY NUMBER(1), --강수형태 (코드값) 0:없음, 1:비, 2:비/눈, 3:눈/비, 4: 눈
    WFKOR VARCHAR2(50), -- 날씨 한국어 (맑음, 구름조금, 구름 많음, 흐림, 비, 눈/비, 눈)
    wfEn varchar2(50), --날씨 영어
    POP NUMBER(3), --강수활률(%)
    REH NUMBER(3), --습도(%)
    WS NUMBER(4,1),--풍속(m/s) 
    WD NUMBER(1), --풍향 0~7(8방) :북, 북동, 동, 남동, 남, 남서, 서, 북서
    wdKor varchar2(50), --풍향 한국어 (동:E, 북:N, 북동:NE, 북서:NW, 남:S, 남동:SE, 남서:SW, 서:W)
    wdEn varchar2(50), --풍향 영어 
    R12 NUMBER(4,1), --12시간 예상 강수량
    S12 NUMBER(4,1), --12시간 예상 적설량
    R06 NUMBER(4,1), --6시간 예상 강수량
    S06 NUMBER(4,1), --6시간 예상 적설량
    PRIMARY KEY (X, Y, "HOUR")
);

CREATE TABLE CATEGORY(
CATE_CODE   NUMBER      PRIMARY KEY,
CATE_NAME   VARCHAR2(50)
);

CREATE TABLE PRODUCT (
PRO_CODE    NUMBER      PRIMARY KEY,
PRO_NAME    VARCHAR2(30),
PRICE       NUMBER,
COMMENTS     VARCHAR2(200),
CATE_CODE   NUMBER      REFERENCES CATEGORY(CATE_CODE)
);

CREATE TABLE SELL (
SELL_DATE   NUMBER,
PRO_CODE    NUMBER      REFERENCES PRODUCT(PRO_CODE),
PRIMARY KEY(SELL_DATE, PRO_CODE)
);

select * from person;
SELECT name, pw, salt FROM person WHERE id = 'iiiii';
ALTER TABLE person ADD salt VARCHAR(300);
ALTER TABLE person ADD passwd VARCHAR2(50);
ALTER TABLE person MODIFY pw VARCHAR2(1024);
