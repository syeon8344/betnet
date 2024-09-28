-- database, table settings

drop database if exists betnet;
create database betnet;
use betnet;

drop table if exists teams;
create table teams (
    teamcode int auto_increment primary key,
    teamname varchar(100) not null,
    logoimage varchar(255)
);

drop table if exists members;
create table members (
    memberid int auto_increment primary key,
    username varchar(50) not null unique,
    password varchar(255) not null,
    name varchar(100) not null,
    contact varchar(20),
    email varchar(255) unique not null,
    gender char(1),
    age int,
    joindate date not null default (current_date),
    teamcode int,
    purchaselimitamount int ,
    purchaselimitcount int default 0,
    points int default 0,
    account varchar(255),
    foreign key(teamcode) references teams(teamcode)
    on update cascade
    on delete cascade
);
select * from members;
drop table if exists teamboard;
create table teamboard (
    postid int auto_increment primary key,
    memberid int not null,
    teamcode int not null,
    content longtext not null,
    title varchar(255) not null,
    createdat date not null default (current_date),
    views int default 0,
    likes int default 0,
    foreign key (memberid) references members(memberid)
    on update cascade
    on delete cascade,
    foreign key (teamcode) references teams(teamcode)
    on update cascade
    on delete cascade
);

drop table if exists comments;
create table comments (
    commentid int auto_increment primary key,
    memberid int not null,
    postid int not null,
    commentindex int not null,
    createdat date not null default (current_date),
    content text not null,
    foreign key (memberid) references members(memberid)
    on update cascade
    on delete cascade,
    foreign key (postid) references teamboard(postid)
    on update cascade
    on delete cascade
);

drop table if exists chatlogs;
create table chatlogs (
    chatlogcode int auto_increment primary key,
    memberid int not null,
    content text not null,
    chatroomuniqueid varchar(50) not null,
    chatdatetime datetime not null default now(),
    foreign key (memberid) references members(memberid)
    on update cascade
    on delete cascade
);

-- 포인트로그 테이블
drop table if exists PointLogs;
CREATE TABLE PointLogs (
    PointLogID INT AUTO_INCREMENT PRIMARY KEY,  -- 포인트 로그 고유 번호 (기본 키, 자동 증가)
    MemberID INT NOT NULL,                     -- 포인트가 증감된 회원의 번호 (외래 키)
    LogDate DATETIME NOT NULL DEFAULT (CURRENT_TIMESTAMP), -- 포인트 증감 발생 날짜
    PointChange INT NOT NULL,                  -- 포인트 증감량 (증가일 경우 양수, 감소일 경우 음수)
    Description int NOT NULL,         -- 포인트 증감 내역 (예: "구매", "적립", "사용")
    FOREIGN KEY (MemberID) REFERENCES Members(MemberID)
    ON UPDATE CASCADE
    ON DELETE CASCADE                          -- 회원 테이블의 외래 키
);

drop table if exists GamePurchaseList;
CREATE TABLE GamePurchaseList (
    ListID INT AUTO_INCREMENT PRIMARY KEY,     -- 게임 구매 목록 고유 번호 (기본 키, 자동 증가)
	PointLogID int ,           -- 포인트로그 (외래키)
    FOREIGN KEY (PointLogID) references PointLogs(PointLogID)  -- 포인트그 테이블의 외래 키
    ON update cascade
    on delete cascade
);

drop table if exists GamePurchaseDetails;
CREATE TABLE GamePurchaseDetails (
    DetailID INT AUTO_INCREMENT PRIMARY KEY,    -- 게임 구매 상세 고유 번호 (기본 키, 자동 증가)
    ListID INT NOT NULL,                       -- 구매 목록 번호 (외래 키)
    MatchID varchar(255) , 						-- 경기목록 고유코드(csv)
	winandloss int NOT NULL ,            -- 회원이 선택한 승패 1 : 승 0 : 패
    matchstate int default 1, -- 경기상태  1 : 경기 정상  0 : 경기취소
    correct int default 0, -- 적중 결과 0: 진행중 1 : 적중 2: 적중실패
    foreign key(ListID) references GamePurchaseList(ListID)
    on update cascade
    on delete cascade
);
-- 회원접속 로그 테이블
drop table if exists access;	
create table access (
	accessid int auto_increment primary key,	-- 접속 로그 번호
    memberid int not null,						-- 멤버 아이디
    memberdatetime datetime not null default now(),	-- 접속 날짜
	foreign key (memberid) references members(memberid)
    on update cascade
    on delete cascade
    
);
