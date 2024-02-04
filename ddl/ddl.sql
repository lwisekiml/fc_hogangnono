-- 동 코드 테이블 생성
CREATE table lawd
(
    lawd_id bigint auto_increment
        primary key,
    lawd_cd char(10)	not null,
    lawd_dong varchar(100) not null,
    exist tinyint(1) not null,
    created_at datetime not null,
    updated_at datetime not null,
    constraint uk_lawdcd
        unique (lawd_cd)
);

-- 아파트 테이블 생성
CREATE table apt
(
    apt_id bigint auto_increment
        primary key,
    apt_name varchar(40)	not null,
    jibun varchar(20) not null,
    dong char(5) not null,
    built_year int not null,
    created_at datetime not null,
    updated_at datetime not null
);

-- 아파트 거래 테이블 생성
CREATE table apt_deal
(
    apt_deal_id bigint auto_increment
        primary key,
    apt_id bigint not null,
    exclusive_area double not null,
    deal_date date not null,
    deal_amount bigint not null,
    floor int not null,
    deal_canceled tinyint(1) default 0 not null,
    deal_canceled_date date null,
    created_at datetime not null,
    updated_at datetime not null
);

-- 아파트 거래 알림 테이블 생성
CREATE table apt_notification
(
    apt_notification bigint auto_increment
        primary key,
    email varchar(100) not null,
    gu_lawd_cd char(5) not null,
    enabled tinyint(1) null,
    created_at datetime not null,
    updated_at datetime not null,
    constraint uk_email_gulawdcd
        unique (email, gu_lawd_cd)
);

---------------------------------------
use house_batch;

select count(*) from house_batch.lawd;

select count(*) from lawd where exist = 0;

select * from lawd where exist = 0 and lawd_dong like "서울특별시%";

---------------------------------------
/* 구 코드 가져오는 쿼리 작성하기 */

select * from lawd;

select distinct lawd_cd from lawd where exist = 1;

select distinct exist from lawd;

select distinct substring(lawd_cd, 1, 5) from lawd where exist = 1 and lawd_cd not like "%00000000";
-- 시, 도에 관한 코드들을 제거해야 합니다.

select * from lawd where exist = 1 and lawd_cd like "%00000000";


/*
1. lawd_dong '동' 문자열이 포함된 데이터들을 조회 -> 5자리를 사용하면 되지 않을까 (X)
-> 군 데이터의 경우에는 리나 읍으로 끝나기 때문에 동이 들어간 데이터만 가져오면 불러와지지 않는다.
2. distinct 사용
*/

---------------------------------------
select * from apt_deal ad left join apt a on a.apt_id = ad.apt_id order by ad.apt_deal_id desc;
