drop database if exists betnet;
create database betnet;
use betnet;


-- 팀 테이블
drop table if exists Teams;
CREATE TABLE Teams (
    TeamCode INT auto_increment PRIMARY KEY,          -- 팀 고유 코드 (기본 키)
    TeamName VARCHAR(100) NOT NULL,           -- 팀 이름
    LogoImage VARCHAR(255)                    -- 팀 로고 이미지 URL 또는 경로
);
select * from Teams;

-- 회원 테이블
drop table if exists members;
CREATE TABLE Members (
    MemberID INT AUTO_INCREMENT PRIMARY KEY,   -- 회원 고유 번호 (기본 키, 자동 증가)
    Username VARCHAR(50) NOT NULL UNIQUE,      -- 회원 아이디 (고유)
    Password VARCHAR(255) NOT NULL,            -- 회원 비밀번호
    Name VARCHAR(100) NOT NULL,                -- 회원 이름
    Contact VARCHAR(20),                       -- 회원 연락처
    Email VARCHAR(255) UNIQUE NOT NULL,        -- 회원 이메일 (고유)
    Gender CHAR(1),                            -- 회원 성별 (예: 'M', 'F')
    Age INT,                                   -- 회원 나이
    JoinDate date NOT NULL default (current_date),                -- 회원 가입일
    TeamCode INT,                 -- 선호하는 팀 (팀 테이블의 외래 키)
    PurchaseLimitAmount int , -- 구매 제한 금액
    PurchaseLimitCount INT DEFAULT 0,          -- 구매 제한 횟수
    Points INT DEFAULT 0,                      -- 회원 포인트
    Account VARCHAR(255),		          -- 계좌 정보
    foreign key(TeamCode) references Teams(TeamCode)
    on update cascade
    on delete cascade
);
select * from members;

-- 팀 게시판 테이블
drop table if exists TeamBoard;
CREATE TABLE TeamBoard (
    PostID INT AUTO_INCREMENT PRIMARY KEY,     -- 게시판 고유 번호 (기본 키, 자동 증가)
    MemberID INT NOT NULL,                     -- 게시물을 작성한 회원의 번호 (외래 키)
    TeamCode INT NOT NULL,            -- 팀 코드 (외래 키)
    Content TEXT NOT NULL,                    -- 게시물 내용
    Title VARCHAR(255) NOT NULL,              -- 게시물 제목
    CreatedAt date NOT NULL default (current_date),              -- 게시물 작성일
    Views INT DEFAULT 0,                      -- 조회수 (기본값 0)
    Likes INT DEFAULT 0,                      -- 추천수 (기본값 0)
    FOREIGN KEY (MemberID) REFERENCES Members(MemberID)  -- 회원 테이블의 외래 키
    on update cascade 
    on delete cascade,
    FOREIGN KEY (TeamCode) REFERENCES Teams(TeamCode)
    on update cascade 
    on delete cascade-- 팀 테이블의 외래 키
);
select * from TeamBoard;

-- 댓글 테이블
DROP table if exists Comments;
CREATE TABLE Comments (
    CommentID INT AUTO_INCREMENT PRIMARY KEY,  -- 댓글 고유 번호 (기본 키, 자동 증가)
    MemberID INT NOT NULL,                     -- 댓글 작성자의 회원 번호 (외래 키)
    PostID INT NOT NULL,                       -- 게시판 번호 (외래 키)
    CommentIndex INT NOT NULL,                 -- 댓글 인덱스
    CreatedAt DATE NOT NULL default (current_date),              -- 댓글 작성일
    Content TEXT NOT NULL,                    -- 댓글 내용
    FOREIGN KEY (MemberID) REFERENCES Members(MemberID)
    ON UPDATE cascade
    ON delete cascade,  -- 회원 테이블의 외래 키
    FOREIGN KEY (PostID) REFERENCES TeamBoard(PostID) 
    ON UPDATE cascade
    ON delete cascade-- 팀 게시판 테이블의 외래 키
);
select * FROM Comments;

-- 채팅 로그 테이블
drop table if exists ChatLogs;
CREATE TABLE ChatLogs (
    ChatLogCode INT AUTO_INCREMENT PRIMARY KEY,  -- 채팅 로그 고유 번호 (기본 키, 자동 증가)
    MemberID INT NOT NULL,                      -- 채팅을 보낸 회원의 번호 (외래 키)
    Content TEXT NOT NULL,                     -- 채팅 내용
    ChatRoomUniqueID VARCHAR(50) NOT NULL,     -- 채팅 방의 고유 번호
    ChatDateTime DATETIME NOT NULL default NOW(),            -- 채팅 발생 일시
    FOREIGN KEY (MemberID) REFERENCES Members(MemberID)
    on update cascade 
    on delete cascade-- 회원 테이블의 외래 키
);
select * from ChatLogs;

-- 포인트로그
drop table if exists PointLogs;
CREATE TABLE PointLogs (
    PointLogID INT AUTO_INCREMENT PRIMARY KEY,  -- 포인트 로그 고유 번호 (기본 키, 자동 증가)
    MemberID INT NOT NULL,                     -- 포인트가 증감된 회원의 번호 (외래 키)
    LogDate datetime NOT NULL DEFAULT (CURRENT_DATE), -- 포인트 증감 발생 날짜
    PointChange INT NOT NULL,                  -- 포인트 증감량 (증가일 경우 양수, 감소일 경우 음수)
    Description int NOT NULL,         -- 포인트 증감 내역 1 = 포인트 충전 , 2 = 배당금 지급 , 3 = 게임 구매 , 4 = 포인트 출금
    FOREIGN KEY (MemberID) REFERENCES Members(MemberID)
    ON UPDATE CASCADE
    ON DELETE CASCADE                          -- 회원 테이블의 외래 키
);
select * from PointLogs;
insert into pointlogs (memberid , pointchange , description) values (1 , 1000 , 3);
select pointlogid from pointlogs order by pointlogid desc limit 1;

-- 게임 구매 목록 테이블
drop table if exists GamePurchaseList;
CREATE TABLE GamePurchaseList (
    ListID INT AUTO_INCREMENT PRIMARY KEY,     -- 게임 구매 목록 고유 번호 (기본 키, 자동 증가)
	pointlogid int ,           -- 포인트로그 (외래키)
    FOREIGN KEY (pointlogid) references pointlogs(pointlogid)  -- 포인트그 테이블의 외래 키
    ON update cascade
    on delete cascade
);
select * from GamePurchaseList;

-- 게임 구매 상세 테이블
drop table if exists GamePurchaseDetails;
CREATE TABLE GamePurchaseDetails (
    DetailID INT AUTO_INCREMENT PRIMARY KEY,    -- 게임 구매 상세 고유 번호 (기본 키, 자동 증가)
    ListID INT NOT NULL,                       -- 구매 목록 번호 (외래 키)
    MatchID varchar(255) , 						-- 경기목록 고유코드(csv) 
	winandloss int NOT NULL ,            -- 회원이 선택한 승패 1 : 승 0 : 패
    matchstate int default 1, -- 경기상태 1 : 경기 정상 0 : 경기취소
    foreign key(ListID) references GamePurchaseList(ListID)
    on update cascade
    on delete cascade
);
select * from GamePurchaseDetails;

-- 샘플 
-- 팀
-- insert into Teams(TeamName , LogoImage) values('KIA' , 'https://ko.wikipedia.org/wiki/KIA_%ED%83%80%EC%9D%B4%EA%B1%B0%EC%A6%88#/media/%ED%8C%8C%EC%9D%BC:Kia_Tigers_emblem_(2021).jpg');
-- insert into Teams(TeamName , LogoImage) values('삼성' , 'https://rogorogo.tistory.com/m/504?pidx=0');
-- insert into Teams(TeamName , LogoImage) values('LG' , 'https://upload.wikimedia.org/wikipedia/ko/8/8a/LG_%ED%8A%B8%EC%9C%88%EC%8A%A4_%EB%A1%9C%EA%B3%A0.png');
-- insert into Teams(TeamName , LogoImage) values('두산' , 'https://i.namu.wiki/i/g7CvCUD6vM7unfzJr6NkNiA7ckFgQ-F013EDVRvt8mbJwxonCb1x-zwXnOAIrvsNuQ_mDNmIKh6soTOGsT2laJvOCrMyk5xw4WEUTJ4NsQIzNldtGE4iQC2eFy8zWvOFD18pbvm0t4IW05yDEnNwMg.svg');
-- insert into Teams(TeamName , LogoImage) values('KT' , 'https://i.namu.wiki/i/TAtaDHRrFcoTW65bC2H_4C9YRBB0JC9RcIcUKWIPr09gSHGjaFTexlDaCw8pUfn-or3OzZBfqmGPRrnvT8MycA.svg');
-- insert into Teams(TeamName , LogoImage) values('한화' , 'https://i.namu.wiki/i/zYiknboFhS0cTRUTNo-WN9veGWGeH61qi1F11ctVIPEZlkkrL20FMOaz7UqRwPittFOGzHF3UbbstRKsLi7nCFux2Gplxo0PN4Z8E7LuJ_SLxHShusxrP0uSixiygjuTtZoiUWw-XyiUt3hv1iShfw.svg');
-- insert into Teams(TeamName , LogoImage) values('SSG' , 'https://i.namu.wiki/i/MtYh_2E_Nw1XucdyX6CAyHyHY4sq4BliYgqu4zBIcMOhD7uphlUgu1I-uZZP1-sQRLv5R342WuYqqp73NVDTWkMYsUZeuE_twQBpH4R5v057ziZh4W5U0LF0hF_wr05r9zx1TwI_7o8jGvD_kc7Heg.svg');
-- insert into Teams(TeamName , LogoImage) values('롯데' , 'https://i.namu.wiki/i/xcGvyRzaU5yYvCV1KIm5bmzk4PXjlAQxHaxgJhq-3jxXB75JMwZjaK_q28Bng1rspvrFaj0IR3CGabj_dy4u93FzG2o5vlaZjB8Inw8sap68m4OLYWWaOIDNJY1TQWvxxBRcyOgPB-jtvCpD3yIlmg.svg');
-- insert into Teams(TeamName , LogoImage) values('NC' , 'https://i.namu.wiki/i/ItRFxzQIgfFsy6kQwk2CA4xZYfOkkta1c6vxtQ-BvX1rpobietq4_pHLnUh0uIFU9EpBd4Bj01OX8SMUjzf30JhZ25NmPSqsD-F_5ynLPzmyZAYwIvmB7RYF0oyzahEFBxhXRfbLoZqOJNblC-17pA.svg');
-- insert into Teams(TeamName , LogoImage) values('키움' , 'https://i.namu.wiki/i/KbcMek3zCSyK8ATT3t1Qm8rBXhK0tjxmSt96rl2vF5FApWR9QKj3Jp1MB48_vblvt-rt6rx_UnEsURtV_z89Jr32rBsOsKdeE9Flk5ocOGiie5N5fdbn9-R_c7Yt-A-j5UwqOmIc8ePGZC17Hgmuzw.svg');
-- insert into Teams(TeamName ) values('자유');
-- insert into Teams(TeamName) values('없음');

-- 멤버
-- INSERT INTO Members (Username, Password, Name, Contact, Email, Gender, Age, TeamCode, PurchaseLimitAmount, PurchaseLimitCount, Points, Account) VALUES
-- ('user001', 'password123', 'John Doe', '010-555-0001', 'johndoe@example.com', 'M', 28, 1, 1000, 5, 100, '123-456-789'),
-- ('user002', 'password456', 'Jane Smith', '010-555-0002', 'janesmith@example.com', 'F', 34, 2, 1500, 3, 200, '987-654-321'),
-- ('user003', 'password789', 'Michael Johnson', '010-555-0003', 'michaeljohnson@example.com', 'M', 22, 3, 2000, 2, 150, '456-789-123'),
-- ('user004', 'password14561', 'Emily Davis', '010-555-0004', 'emilydavis@example.com', 'F', 29, 4, 1200, 4, 250, '321-654-987'),
-- ('user005', 'password15612', 'Robert Brown', '010-555-0005', 'robertbrown@example.com', 'M', 31, 5, 1700, 6, 300, '654-321-987'),
-- ('user006', 'password14515', 'Sarah Wilson', '010-555-0006', 'sarahwilson@example.com', 'F', 27, 6, 1100, 7, 110, '852-741-963'),
-- ('user007', 'password1233215', 'James Lee', '010-555-0007', 'jamslee@example.com', 'M', 35, 7, 1400, 5, 120, '951-753-486'),
-- ('user008', 'password1556', 'Laura White', '010-555-0008', 'laurawhite@example.com', 'F', 40, 8, 1300, 8, 130, '357-468-951'),
-- ('user009', 'password1561', 'David Harris', '010-555-0009', 'davidharris@example.com', 'M', 23, 9, 1600, 4, 140, '468-951-357'),
-- ('user010', 'password35412', 'Anna Martin', '010-555-0010', 'annamartin@example.com', 'F', 30, 10, 1500, 6, 150, '741-852-963'),
-- ('user011', 'password11256', 'Daniel Robinson', '010-555-0011', 'danielrobinson@example.com', 'M', 29, 1, 1800, 3, 160, '852-963-741'),
-- ('user012', 'password15451', 'Olivia Clark', '010-555-0012', 'oliviaclark@example.com', 'F', 24, 2, 1900, 7, 170, '963-741-852'),
-- ('user013', 'password1234456', 'Matthew Lewis', '010-555-0013', 'matthewlewis@example.com', 'M', 32, 3, 2000, 5, 180, '741-963-852'),
-- ('user014', 'password11565', 'Sophia Allen', '010-555-0014', 'sophiaallen@example.com', 'F', 27, 4, 1100, 8, 190, '852-741-963'),
-- ('user015', 'password123215', 'Liam Young', '010-555-0015', 'liamyoung@example.com', 'M', 36, 5, 1200, 2, 200, '963-852-741'),
-- ('user016', 'password122151', 'Ava King', '010-555-0016', 'avaking@example.com', 'F', 33, 6, 1300, 4, 210, '741-852-963'),
-- ('user017', 'password5345', 'Ethan Wright', '010-555-0017', 'ethanwright@example.com', 'M', 26, 7, 1400, 6, 220, '852-963-741'),
-- ('user018', 'password11321', 'Mia Scott', '010-555-0018', 'miascott@example.com', 'F', 28, 8, 1500, 3, 230, '963-741-852'),
-- ('user019', 'password125613', 'Alexander Hill', '010-555-0019', 'alexanderhill@example.com', 'M', 31, 9, 1600, 5, 240, '741-963-852'),
-- ('user020', 'password115136', 'Isabella Adams', '010-555-0020', 'isabellaadams@example.com', 'F', 25, 10, 1700, 7, 250, '852-741-963'),
-- ('user021', 'password125451', 'Benjamin Baker', '010-555-0021', 'benjaminbaker@example.com', 'M', 30, 1, 1800, 2, 260, '963-852-741'),
-- ('user022', 'password1513', 'Charlotte Nelson', '010-555-0022', 'charlottenelson@example.com', 'F', 29, 2, 1900, 4, 270, '741-963-852'),
-- ('user023', 'password123133', 'William Carter', '010-555-0023', 'williamcarter@example.com', 'M', 34, 3, 2000, 6, 280, '852-963-741'),
-- ('user024', 'password122133', 'Amelia Mitchell', '010-555-0024', 'ameliamitchell@example.com', 'F', 31, 4, 2100, 3, 290, '963-741-852'),
-- ('user025', 'password123321', 'James Thompson', '010-555-0025', 'jamesthompson@example.com', 'M', 26, 5, 2200, 5, 300, '741-852-963'),
-- ('user026', 'password123215', 'Harper Turner', '010-555-0026', 'harperturner@example.com', 'F', 32, 6, 2300, 7, 310, '852-963-741'),
-- ('user027', 'password123878', 'Lucas Harris', '010-555-0027', 'lucasharris@example.com', 'M', 25, 7, 2400, 2, 320, '963-741-852'),
-- ('user028', 'password122156', 'Ella Morris', '010-555-0028', 'ellamorris@example.com', 'F', 28, 8, 2500, 4, 330, '741-852-963'),
-- ('user029', 'password124523', 'Noah Wilson', '010-555-0029', 'noahwilson@example.com', 'M', 27, 9, 2600, 6, 340, '852-963-741'),
-- ('user030', 'password1235456', 'Lily Davis', '010-555-0030', 'lilydavis@example.com', 'F', 34, 10, 2700, 3, 350, '963-741-852'),
-- ('user031', 'password125458', 'Elijah Thompson', '010-555-0031', 'elijahthompson@example.com', 'M', 22, 1, 2800, 5, 360, '741-852-963'),
-- ('user032', 'password125454', 'Sofia Young', '010-555-0032', 'sofiayoung@example.com', 'F', 30, 2, 2900, 7, 370, '852-963-741'),
-- ('user033', 'password125464', 'Mason Martinez', '010-555-0033', 'masonmartinez@example.com', 'M', 31, 3, 3000, 4, 380, '963-741-852'),
-- ('user034', 'password12448', 'Mia Walker', '010-555-0034', 'miawalker@example.com', 'F', 26, 4, 3100, 6, 390, '741-852-963'),
-- ('user035', 'password123548', 'Logan Hall', '010-555-0035', 'loganhall@example.com', 'M', 29, 5, 3200, 2, 400, '852-963-741'),
-- ('user036', 'password124451', 'Avery Allen', '010-555-0036', 'averyallen@example.com', 'F', 27, 6, 3300, 5, 410, '963-741-852'),
-- ('user037', 'password12451', 'Oliver King', '010-555-0037', 'oliverking@example.com', 'M', 34, 7, 3400, 8, 420, '741-852-963'),
-- ('user038', 'password12354', 'Ella Green', '010-555-0038', 'ellagreen@example.com', 'F', 22, 8, 3500, 3, 430, '852-963-741'),
-- ('user039', 'password123545', 'James Lee', '010-555-0039', 'jameslee@example.com', 'M', 28, 9, 3600, 4, 440, '963-741-852'),
-- ('user040', 'password12556', 'Grace Robinson', '010-555-0040', 'gracerobinson@example.com', 'F', 32, 10, 3700, 5, 450, '741-852-963'),
-- ('user041', 'password12215', 'Henry Davis', '010-555-0041', 'henrydavis@example.com', 'M', 25, 1, 3800, 7, 460, '852-963-741'),
-- ('user042', 'password123818', 'Natalie Carter', '010-555-0042', 'nataliecarter@example.com', 'F', 31, 2, 3900, 2, 470, '963-741-852'),
-- ('user043', 'password1232516', 'Samuel Evans', '010-555-0043', 'samuelevans@example.com', 'M', 28, 3, 4000, 5, 480, '741-852-963'),
-- ('user044', 'password12358', 'Madison Phillips', '010-555-0044', 'madisonphillips@example.com', 'F', 30, 4, 4100, 6, 490, '852-963-741'),
-- ('user045', 'password123546', 'Jacob Wilson', '010-555-0045', 'jacobwilson@example.com', 'M', 29, 5, 4200, 3, 500, '963-741-852'),
-- ('user046', 'password12185', 'Chloe Mitchell', '010-555-0046', 'chloemitchell@example.com', 'F', 25, 6, 4300, 4, 510, '741-852-963'),
-- ('user047', 'password12548', 'Isaac Moore', '010-555-0047', 'isaacmoore@example.com', 'M', 27, 7, 4400, 5, 520, '852-963-741'),
-- ('user048', 'password12365', 'Zoe White', '010-555-0048', 'zoewhite@example.com', 'F', 30, 8, 4500, 2, 530, '963-741-852'),
-- ('user049', 'password123348', 'Jack Harris', '010-555-0049', 'jackharris@example.com', 'M', 34, 9, 4600, 6, 540, '741-852-963'),
-- ('user050', 'password1231231', 'Luna Young', '010-555-0050', 'lunayoung@example.com', 'F', 28, 10, 4700, 3, 550, '852-963-741');
-- select * FROM MEMBERS;

-- 게시판
-- INSERT INTO TeamBoard (memberid, teamcode, content, title, views, likes) VALUES
-- (1, 1, '이것은 Team1의 첫 번째 게시물 내용입니다.', 'Team1 첫 번째 게시물', 10, 5),
-- (2, 2, 'Team2의 두 번째 게시물 내용을 보여줍니다.', 'Team2 두 번째 게시물', 15, 10),
-- (3, 3, 'Team3의 세 번째 게시물에 대한 자세한 내용입니다.', 'Team3 세 번째 게시물', 5, 2),
-- (4, 4, '여기 Team4의 네 번째 게시물 내용이 있습니다.', 'Team4 네 번째 게시물', 8, 4),
-- (5, 5, 'Team5의 다섯 번째 게시물에 대한 내용입니다.', 'Team5 다섯 번째 게시물', 20, 8),
-- (6, 6, 'Team6의 여섯 번째 게시물입니다.', 'Team6 여섯 번째 게시물', 12, 7),
-- (7, 7, 'Team7의 일곱 번째 게시물에 대한 흥미로운 내용입니다.', 'Team7 일곱 번째 게시물', 18, 12),
-- (8, 8, 'Team8의 여덟 번째 게시물 내용입니다.', 'Team8 여덟 번째 게시물', 6, 3),
-- (9, 9, 'Team9의 아홉 번째 게시물 내용입니다.', 'Team9 아홉 번째 게시물', 9, 5),
-- (10, 10, 'Team10의 열 번째 게시물에 대한 세부 사항입니다.', 'Team10 열 번째 게시물', 22, 10),
-- (11, 1, 'Team1의 열한 번째 게시물 내용입니다.', 'Team1 열한 번째 게시물', 14, 6),
-- (12, 2, 'Team2의 열두 번째 게시물 내용입니다.', 'Team2 열두 번째 게시물', 25, 15),
-- (13, 3, 'Team3의 열세 번째 게시물 내용입니다.', 'Team3 열세 번째 게시물', 7, 4),
-- (14, 4, 'Team4의 열네 번째 게시물 내용입니다.', 'Team4 열네 번째 게시물', 11, 8),
-- (15, 5, 'Team5의 열다섯 번째 게시물 내용입니다.', 'Team5 열다섯 번째 게시물', 24, 12),
-- (16, 6, 'Team6의 열여섯 번째 게시물 내용입니다.', 'Team6 열여섯 번째 게시물', 13, 9),
-- (17, 7, 'Team7의 열일곱 번째 게시물 내용입니다.', 'Team7 열일곱 번째 게시물', 19, 11),
-- (18, 8, 'Team8의 열여덟 번째 게시물 내용입니다.', 'Team8 열여덟 번째 게시물', 8, 5),
-- (19, 9, 'Team9의 열아홉 번째 게시물 내용입니다.', 'Team9 열아홉 번째 게시물', 10, 7),
-- (20, 10, 'Team10의 스무 번째 게시물 내용입니다.', 'Team10 스무 번째 게시물', 26, 14),
-- (21, 1, 'Team1의 스물한 번째 게시물 내용입니다.', 'Team1 스물한 번째 게시물', 15, 10),
-- (22, 2, 'Team2의 스물두 번째 게시물 내용입니다.', 'Team2 스물두 번째 게시물', 23, 13),
-- (23, 3, 'Team3의 스물세 번째 게시물 내용입니다.', 'Team3 스물세 번째 게시물', 9, 6),
-- (24, 4, 'Team4의 스물네 번째 게시물 내용입니다.', 'Team4 스물네 번째 게시물', 12, 8),
-- (25, 5, 'Team5의 스물다섯 번째 게시물 내용입니다.', 'Team5 스물다섯 번째 게시물', 27, 15),
-- (26, 6, 'Team6의 스물여섯 번째 게시물 내용입니다.', 'Team6 스물여섯 번째 게시물', 16, 11),
-- (27, 7, 'Team7의 스물일곱 번째 게시물 내용입니다.', 'Team7 스물일곱 번째 게시물', 21, 14),
-- (28, 8, 'Team8의 스물여덟 번째 게시물 내용입니다.', 'Team8 스물여덟 번째 게시물', 11, 7),
-- (29, 9, 'Team9의 스물아홉 번째 게시물 내용입니다.', 'Team9 스물아홉 번째 게시물', 13, 9),
-- (30, 10, 'Team10의 서른 번째 게시물 내용입니다.', 'Team10 서른 번째 게시물', 28, 16),
-- (31, 1, 'Team1의 서른한 번째 게시물 내용입니다.', 'Team1 서른한 번째 게시물', 18, 12),
-- (32, 2, 'Team2의 서른두 번째 게시물 내용입니다.', 'Team2 서른두 번째 게시물', 24, 17),
-- (33, 3, 'Team3의 서른세 번째 게시물 내용입니다.', 'Team3 서른세 번째 게시물', 10, 8),
-- (34, 4, 'Team4의 서른네 번째 게시물 내용입니다.', 'Team4 서른네 번째 게시물', 14, 10),
-- (35, 5, 'Team5의 서른다섯 번째 게시물 내용입니다.', 'Team5 서른다섯 번째 게시물', 29, 18),
-- (36, 6, 'Team6의 서른여섯 번째 게시물 내용입니다.', 'Team6 서른여섯 번째 게시물', 20, 13),
-- (37, 7, 'Team7의 서른일곱 번째 게시물 내용입니다.', 'Team7 서른일곱 번째 게시물', 24, 20),
-- (38, 8, 'Team8의 서른여덟 번째 게시물 내용입니다.', 'Team8 서른여덟 번째 게시물', 12, 9),
-- (39, 9, 'Team9의 서른아홉 번째 게시물 내용입니다.', 'Team9 서른아홉 번째 게시물', 17, 12),
-- (40, 10, 'Team10의 마흔 번째 게시물 내용입니다.', 'Team10 마흔 번째 게시물', 32, 22),
-- (41, 1, 'Team1의 마흔한 번째 게시물 내용입니다.', 'Team1 마흔한 번째 게시물', 22, 14),
-- (42, 2, 'Team2의 마흔두 번째 게시물 내용입니다.', 'Team2 마흔두 번째 게시물', 26, 18),
-- (43, 3, 'Team3의 마흔세 번째 게시물 내용입니다.', 'Team3 마흔세 번째 게시물', 14, 10),
-- (44, 4, 'Team4의 마흔네 번째 게시물 내용입니다.', 'Team4 마흔네 번째 게시물', 17, 12),
-- (45, 5, 'Team5의 마흔다섯 번째 게시물 내용입니다.', 'Team5 마흔다섯 번째 게시물', 32, 22),
-- (46, 6, 'Team6의 마흔여섯 번째 게시물 내용입니다.', 'Team6 마흔여섯 번째 게시물', 24, 16),
-- (47, 7, 'Team7의 마흔일곱 번째 게시물 내용입니다.', 'Team7 마흔일곱 번째 게시물', 28, 20),
-- (48, 8, 'Team8의 마흔여덟 번째 게시물 내용입니다.', 'Team8 마흔여덟 번째 게시물', 16, 12),
-- (49, 9, 'Team9의 마흔아홉 번째 게시물 내용입니다.', 'Team9 마흔아홉 번째 게시물', 19, 14),
-- (50, 10, 'Team10의 오십 번째 게시물 내용입니다.', 'Team10 오십 번째 게시물', 35, 25);

-- 댓글
-- insert into comments (memberid, postid, commentindex, createdat, content) values
-- (1, 1, 1, CURRENT_DATE, '이 게시물에 대한 첫 번째 댓글입니다.'),
-- (2, 1, 2, CURRENT_DATE, '정말 유용한 정보네요. 감사합니다!'),
-- (3, 1, 3, CURRENT_DATE, '이 팀에 대한 더 많은 정보를 알고 싶습니다.'),
-- (4, 2, 1, CURRENT_DATE, 'Team2의 게시물이 좋습니다.'),
-- (5, 2, 2, CURRENT_DATE, '이 팀의 최근 성과가 궁금하네요.'),
-- (6, 2, 3, CURRENT_DATE, '팀2에 대한 자세한 분석이 필요합니다.'),
-- (7, 3, 1, CURRENT_DATE, 'Team3의 게시물에 대한 댓글입니다.'),
-- (8, 3, 2, CURRENT_DATE, '좋은 글이지만 좀 더 자세히 설명해주셨으면 좋겠어요.'),
-- (9, 3, 3, CURRENT_DATE, '이 팀의 전략에 대해 더 알고 싶습니다.'),
-- (10, 4, 1, CURRENT_DATE, 'Team4 게시물 내용이 유익합니다.'),
-- (11, 4, 2, CURRENT_DATE, '이 팀의 전반적인 상황을 알고 싶습니다.'),
-- (12, 4, 3, CURRENT_DATE, '다음 게시물에서 개선점을 알려주세요.'),
-- (13, 5, 1, CURRENT_DATE, 'Team5의 게시물이 흥미롭네요.'),
-- (14, 5, 2, CURRENT_DATE, '이 팀의 지난 성과를 리뷰해보는 것도 좋을 듯합니다.'),
-- (15, 5, 3, CURRENT_DATE, '팀5의 향후 계획에 대한 의견이 궁금합니다.'),
-- (16, 6, 1, CURRENT_DATE, 'Team6의 게시물 잘 읽었습니다.'),
-- (17, 6, 2, CURRENT_DATE, '이 팀의 최근 활동을 공유해주셔서 감사합니다.'),
-- (18, 6, 3, CURRENT_DATE, '추가적인 정보가 필요합니다.'),
-- (19, 7, 1, CURRENT_DATE, 'Team7에 대한 첫 번째 댓글입니다.'),
-- (20, 7, 2, CURRENT_DATE, '좋은 게시물이지만 몇 가지 의문이 있습니다.'),
-- (21, 7, 3, CURRENT_DATE, '이 팀의 비전과 목표가 궁금합니다.'),
-- (22, 8, 1, CURRENT_DATE, 'Team8의 게시물 내용이 흥미롭습니다.'),
-- (23, 8, 2, CURRENT_DATE, '이 팀에 대한 분석이 필요합니다.'),
-- (24, 8, 3, CURRENT_DATE, '더 많은 데이터와 사실이 포함되면 좋겠습니다.'),
-- (25, 9, 1, CURRENT_DATE, 'Team9의 게시물 잘 읽었습니다.'),
-- (26, 9, 2, CURRENT_DATE, '이 팀의 전략과 계획에 대한 자세한 정보가 필요합니다.'),
-- (27, 9, 3, CURRENT_DATE, '팀9의 성과를 어떻게 개선할 수 있을까요?'),
-- (28, 10, 1, CURRENT_DATE, 'Team10의 게시물 내용이 매우 유익합니다.'),
-- (29, 10, 2, CURRENT_DATE, '이 팀의 과거와 현재를 비교하는 것도 좋을 것 같습니다.'),
-- (30, 10, 3, CURRENT_DATE, 'Team10의 향후 계획에 대한 세부사항을 알고 싶습니다.'),
-- (31, 1, 4, CURRENT_DATE, '이 게시물에 대해 추가적으로 질문이 있습니다.'),
-- (32, 2, 4, CURRENT_DATE, '팀2의 최근 경기 결과가 궁금합니다.'),
-- (33, 3, 4, CURRENT_DATE, 'Team3의 전술에 대한 추가적인 정보가 필요합니다.'),
-- (34, 4, 4, CURRENT_DATE, '이 팀의 발전 가능성에 대해 논의해봅시다.'),
-- (35, 5, 4, CURRENT_DATE, '팀5의 새로운 전략이 기대됩니다.'),
-- (36, 6, 4, CURRENT_DATE, 'Team6의 최신 뉴스가 궁금합니다.'),
-- (37, 7, 4, CURRENT_DATE, '이 게시물의 주제에 대해 더 자세히 알고 싶습니다.'),
-- (38, 8, 4, CURRENT_DATE, 'Team8의 게시물에 대해 다른 의견이 있습니다.'),
-- (39, 9, 4, CURRENT_DATE, '팀9의 최근 성과를 분석해보는 것도 좋을 것 같습니다.'),
-- (40, 10, 4, CURRENT_DATE, '이 팀의 전략을 분석한 글이 기대됩니다.'),
-- (41, 1, 5, CURRENT_DATE, '게시물의 데이터 분석이 필요합니다.'),
-- (42, 2, 5, CURRENT_DATE, 'Team2의 지난 시즌 결과를 검토해보세요.'),
-- (43, 3, 5, CURRENT_DATE, '이 팀의 주요 선수들에 대해 더 알고 싶습니다.'),
-- (44, 4, 5, CURRENT_DATE, 'Team4의 성과 분석이 필요합니다.'),
-- (45, 5, 5, CURRENT_DATE, '팀5의 전술에 대한 상세 분석을 원합니다.'),
-- (46, 6, 5, CURRENT_DATE, '이 팀의 향후 계획에 대해 논의해보세요.'),
-- (47, 7, 5, CURRENT_DATE, 'Team7의 전술에 대한 의견이 궁금합니다.'),
-- (48, 8, 5, CURRENT_DATE, '팀8의 경기력에 대해 더 논의해봅시다.'),
-- (49, 9, 5, CURRENT_DATE, 'Team9의 향후 계획을 공유해주시면 좋겠습니다.'),
-- (50, 10, 5, CURRENT_DATE, '이 팀의 전반적인 성과를 평가해보는 것도 좋습니다.');

-- 채팅
-- insert into chatlogs (memberid, content, chatroomuniqueid) values
-- (1, '안녕하세요! 팀의 최신 소식이 궁금합니다.', 'A1B2C3D4E5'),
-- (2, '안녕하세요! 최근 경기 결과를 알려주세요.', 'F6G7H8I9J0'),
-- (3, '오늘 회의는 몇 시인가요?', 'K1L2M3N4O5'),
-- (4, '팀원들 모두 잘 지내고 있나요?', 'P6Q7R8S9T0'),
-- (5, '새로운 전략에 대해 논의해봐야 할 것 같습니다.', 'U1V2W3X4Y5'),
-- (6, '다음 경기 일정이 어떻게 되나요?', 'Z6A7B8C9D0'),
-- (7, '이 프로젝트의 진척 상황을 알고 싶습니다.', 'E1F2G3H4I5'),
-- (8, '팀 회의 일정을 조정해볼까요?', 'J6K7L8M9N0'),
-- (9, '새로운 멤버가 추가되었나요?', 'O1P2Q3R4S5'),
-- (10, '팀의 최근 성과를 분석해 주세요.', 'T6U7V8W9X0'),
-- (11, '이번 주 회의는 언제인가요?', 'Y1Z2A3B4C5'),
-- (12, '팀원들께 최근 업데이트를 공유해 주세요.', 'D6E7F8G9H0'),
-- (13, '이 채팅 방의 주제는 무엇인가요?', 'I1J2K3L4M5'),
-- (14, '팀의 최근 뉴스가 무엇인가요?', 'N6O7P8Q9R0'),
-- (15, '새로운 팀 전략에 대한 의견을 나눕시다.', 'S1T2U3V4W5'),
-- (16, '팀의 현재 상황을 업데이트해 주세요.', 'X6Y7Z8A9B0'),
-- (17, '새로운 프로젝트에 대한 정보가 필요합니다.', 'C1D2E3F4G5'),
-- (18, '팀원들과의 소통이 필요합니다.', 'H6I7J8K9L0'),
-- (19, '이번 달 목표는 무엇인가요?', 'M1N2O3P4Q5'),
-- (20, '팀의 최근 작업을 확인해 주세요.', 'R6S7T8U9V0'),
-- (21, '다음 팀 미팅의 세부 사항을 알려주세요.', 'W1X2Y3Z4A5'),
-- (22, '팀의 진행 상황을 보고해 주세요.', 'B6C7D8E9F0'),
-- (23, '팀 프로젝트의 마감일이 언제인가요?', 'G1H2I3J4K5'),
-- (24, '회의 자료를 공유해 주세요.', 'L6M7N8O9P0'),
-- (25, '팀원들의 피드백을 요청합니다.', 'Q1R2S3T4U5'),
-- (26, '프로젝트의 현재 진행 상황을 알 수 있을까요?', 'V6W7X8Y9Z0'),
-- (27, '새로운 전략을 팀원들과 공유합시다.', 'A1B2C3D4E6'),
-- (28, '팀 회의에서 논의할 주제를 정리해 주세요.', 'F7G8H9I0J1'),
-- (29, '최근 프로젝트의 문제를 해결해야 합니다.', 'K2L3M4N5O6'),
-- (30, '다음 주에 예정된 이벤트가 무엇인가요?', 'P7Q8R9S0T1'),
-- (31, '팀원들과의 의견 교환이 필요합니다.', 'U2V3W4X5Y6'),
-- (32, '팀의 목표와 방향을 명확히 해야 합니다.', 'Z7A8B9C0D1'),
-- (33, '새로운 팀 구성원에 대해 소개해 주세요.', 'E2F3G4H5I6'),
-- (34, '팀의 향후 계획을 논의합시다.', 'J7K8L9M0N1'),
-- (35, '현재 진행 중인 작업에 대해 알려주세요.', 'O2P3Q4R5S6'),
-- (36, '팀의 주요 목표를 설정합시다.', 'T7U8V9W0X1'),
-- (37, '이번 주 회의 일정에 대해 알림이 필요합니다.', 'Y2Z3A4B5C6'),
-- (38, '팀의 새로운 업데이트가 있나요?', 'D7E8F9G0H1'),
-- (39, '프로젝트의 세부 사항을 재검토해 주세요.', 'I2J3K4L5M6'),
-- (40, '팀의 주요 성과를 정리해 주세요.', 'N7O8P9Q0R1'),
-- (41, '회의 자료를 미리 보내주세요.', 'S2T3U4V5W6'),
-- (42, '팀의 진행 상황을 업데이트합시다.', 'X7Y8Z9A0B1'),
-- (43, '프로젝트의 다음 단계가 궁금합니다.', 'C2D3E4F5G6'),
-- (44, '팀원들의 의견을 모아서 정리해 주세요.', 'H7I8J9K0L1'),
-- (45, '이번 달 작업 목표를 확인해 주세요.', 'M2N3O4P5Q6'),
-- (46, '팀의 주요 프로젝트를 검토합시다.', 'R7S8T9U0V1'),
-- (47, '팀원들과의 소통을 강화해야 합니다.', 'W2X3Y4Z5A6'),
-- (48, '프로젝트의 현재 상태를 알 수 있을까요?', 'B7C8D9E0F1'),
-- (49, '새로운 전략에 대해 팀원들과 논의합시다.', 'G2H3I4J5K6'),
-- (50, '팀의 향후 계획을 수립해 주세요.', 'L7M8N9O0P1');



-- 포인트
-- 샘플 데이터 삽입 스크립트
-- INSERT INTO PointLogs (MemberID, PointChange, Description)
-- VALUES
-- 양수 포인트 변경 (Description = 1)
-- (1, 100, 1),
-- (2, 150, 1),
-- (3, 120, 1),
-- (4, 180, 1),
-- (5, 110, 1),
-- (6, 200, 1),
-- (7, 130, 1),
-- (8, 140, 1),
-- (9, 160, 1),
-- (10, 170, 1),
-- (11, 110, 1),
-- (12, 150, 1),
-- (13, 140, 1),
-- (14, 190, 1),
-- (15, 160, 1),
-- (16, 120, 1),
-- (17, 180, 1),
-- (18, 200, 1),
-- (19, 130, 1),
-- (20, 150, 1),
-- (21, 140, 1),
-- (22, 170, 1),
-- (23, 190, 1),
-- (24, 110, 1),
-- (25, 160, 1),
-- (26, 130, 1),
-- (27, 200, 1),
-- (28, 150, 1),
-- (29, 120, 1),
-- (30, 180, 1),
-- (31, 190, 1),
-- (32, 140, 1),
-- (33, 170, 1),
-- (34, 110, 1),
-- (35, 200, 1),
-- (36, 130, 1),
-- (37, 150, 1),
-- (38, 160, 1),
-- (39, 180, 1),
-- (40, 120, 1),
-- (41, 190, 1),
-- (42, 150, 1),
-- (43, 140, 1),
-- (44, 170, 1),
-- (45, 200, 1),
-- (46, 130, 1),
-- (47, 180, 1),
-- (48, 160, 1),
-- (49, 110, 1),
-- (50, 150, 1),
-- 배당금 지급 (Description = 2)
-- (1, 200, 2),
-- (2, 180, 2),
-- (3, 150, 2),
-- (4, 170, 2),
-- (5, 160, 2),
-- (6, 190, 2),
-- (7, 120, 2),
-- (8, 130, 2),
-- (9, 200, 2),
-- (10, 150, 2),
-- (11, 180, 2),
-- (12, 170, 2),
-- (13, 160, 2),
-- (14, 200, 2),
-- (15, 130, 2),
-- (16, 190, 2),
-- (17, 120, 2),
-- (18, 150, 2),
-- (19, 200, 2),
-- (20, 170, 2),
-- (21, 160, 2),
-- (22, 180, 2),
-- (23, 130, 2),
-- (24, 200, 2),
-- (25, 190, 2),
-- (26, 150, 2),
-- (27, 170, 2),
-- (28, 120, 2),
-- (29, 160, 2),
-- (30, 200, 2),
-- (31, 180, 2),
-- (32, 130, 2),
-- (33, 190, 2),
-- (34, 160, 2),
-- (35, 150, 2),
-- (36, 200, 2),
-- (37, 170, 2),
-- (38, 120, 2),
-- (39, 180, 2),
-- (40, 130, 2),
-- (41, 190, 2),
-- (42, 160, 2),
-- (43, 200, 2),
-- (44, 150, 2),
-- (45, 170, 2),
-- (46, 120, 2),
-- (47, 200, 2),
-- (48, 190, 2),
-- (49, 160, 2),
-- (50, 130, 2),
-- 게임 구매 (Description = 3)
-- (1, -100, 3),
-- (2, -150, 3),
-- (3, -120, 3),
-- (4, -180, 3),
-- (5, -110, 3),
-- (6, -200, 3),
-- (7, -130, 3),
-- (8, -140, 3),
-- (9, -160, 3),
-- (10, -170, 3),
-- (11, -110, 3),
-- (12, -150, 3),
-- (13, -140, 3),
-- (14, -190, 3),
-- (15, -160, 3),
-- (16, -120, 3),
-- (17, -180, 3),
-- (18, -200, 3),
-- (19, -130, 3),
-- (20, -150, 3),
-- (21, -140, 3),
-- (22, -170, 3),
-- (23, -190, 3),
-- (24, -110, 3),
-- (25, -160, 3),
-- (26, -130, 3),
-- (27, -200, 3),
-- (28, -150, 3),
-- (29, -120, 3),
-- (30, -180, 3),
-- (31, -190, 3),
-- (32, -140, 3),
-- (33, -170, 3),
-- (34, -110, 3),
-- (35, -200, 3),
-- (36, -130, 3),
-- (37, -150, 3),
-- (38, -160, 3),
-- (39, -180, 3),
-- (40, -120, 3),
-- (41, -190, 3),
-- (42, -150, 3),
-- (43, -140, 3),
-- (44, -170, 3),
-- (45, -200, 3),
-- (46, -130, 3),
-- (47, -180, 3),
-- (48, -160, 3),
-- (49, -110, 3),
-- (50, -150, 3),
-- 포인트 출금 (Description = 4)
-- (1, -200, 4),
-- (2, -180, 4),
-- (3, -150, 4),
-- (4, -170, 4),
-- (5, -160, 4),
-- (6, -190, 4),
-- (7, -120, 4),
-- (8, -130, 4),
-- (9, -200, 4),
-- (10, -150, 4),
-- (11, -180, 4),
-- (12, -170, 4),
-- (13, -160, 4),
-- (14, -200, 4),
-- (15, -130, 4),
-- (16, -190, 4),
-- (17, -120, 4),
-- (18, -150, 4),
-- (19, -200, 4),
-- (20, -170, 4),
-- (21, -160, 4),
-- (22, -180, 4),
-- (23, -130, 4),
-- (24, -200, 4),
-- (25, -190, 4),
-- (26, -150, 4),
-- (27, -170, 4),
-- (28, -120, 4),
-- (29, -160, 4),
-- (30, -200, 4),
-- (31, -180, 4),
-- (32, -130, 4),
-- (33, -190, 4),
-- (34, -160, 4),
-- (35, -150, 4),
-- (36, -200, 4),
-- (37, -170, 4),
-- (38, -120, 4),
-- (39, -180, 4),
-- (40, -130, 4),
-- (41, -190, 4),
-- (42, -160, 4),
-- (43, -200, 4),
-- (44, -150, 4),
-- (45, -170, 4),
-- (46, -120, 4),
-- (47, -200, 4),
-- (48, -190, 4),
-- (49, -160, 4),
-- (50, -130, 4);

-- 게임구매목록
-- insert into gamepurchaselist (pointlogid) values
-- (101),
-- (102),
-- (103),
-- (104),
-- (105),
-- (106),
-- (107),
-- (108),
-- (109),
-- (110),
-- (111),
-- (112),
-- (113),
-- (114),
-- (115),
-- (116),
-- (117),
-- (118),
-- (119),
-- (120),
-- (121),
-- (122),
-- (123),
-- (124),
-- (125),
-- (126),
-- (127),
-- (128),
-- (129),
-- (130),
-- (131),
-- (132),
-- (133),
-- (134),
-- (135),
-- (136),
-- (137),
-- (138),
-- (139),
-- (140),
-- (141),
-- (142),
-- (143),
-- (144),
-- (145),
-- (146),
-- (147),
-- (148),
-- (149),
-- (150);

-- 게임구매상세
-- insert into gamepurchasedetails (listid, matchid, winandloss , matchstate) values
-- (1, '20240901-1-1400', 1 ,0),
-- (2, '20240901-2-1500', 1 , 1),
-- (3, '20240901-2-1500', 1 , 1),
-- (3, '20240901-3-1600', 1 , 1),
-- (4, '20240901-4-1700', 0 , 1),
-- (5, '20240901-5-1800', 1 ,1),
-- (6, '20240901-6-1900', 0 ,1),
-- (7, '20240901-7-2000', 1 ,1),
-- (7, '20240901-8-2100', 1 ,1),
-- (7, '20240901-8-1500', 0 ,1),
-- (8, '20240901-8-2100', 0 ,1),
-- (9, '20240901-9-2200', 1 ,1),
-- (10, '20240901-10-2300', 1, 1),
-- (11, '20240902-1-2400', 0 ,1),
-- (12, '20240902-2-1400', 1 ,1),
-- (13, '20240902-3-1500', 1 ,1),
-- (14, '20240902-4-1600', 1 ,1),
-- (15, '20240902-5-1700', 0 ,0),
-- (16, '20240902-6-1500', 1 ,1),
-- (17, '20240902-7-2300', 0 ,1),
-- (18, '20240902-8-1400', 0 ,1),
-- (19, '20240902-9-1400', 1 ,1),
-- (20, '20240902-10-1500', 1 ,1),
-- (21, '20240903-1-1700', 0 ,1),
-- (22, '20240903-2-1300', 0 ,1),
-- (23, '20240903-3-1500', 1 ,1),
-- (23, '20240903-4-1600', 1 ,1),
-- (23, '20240903-6-1400', 1 ,1),
-- (24, '20240903-4-1600', 0 ,1),
-- (25, '20240903-5-1200', 1 ,1),
-- (26, '20240903-6-1400', 0 ,1),
-- (27, '20240903-7-1500', 1 ,1),
-- (28, '20240903-8-1600', 0 ,1),
-- (29, '20240903-9-1700', 0 ,1),
-- (30, '20240903-10-1500', 1 ,1),
-- (31, '20240904-1-1600', 1 ,1),
-- (32, '20240904-2-1200', 0 ,1),
-- (33, '20240904-3-1300', 0 ,1),
-- (34, '20240904-4-1500', 0 ,1),
-- (35, '20240904-5-1700', 1 ,1),
-- (36, '20240904-6-1800', 0 ,1),
-- (37, '20240904-7-1900', 1 ,1),
-- (38, '20240904-8-2000', 0 ,1),
-- (39, '20240904-9-2100', 1 ,1),
-- (39, '20240904-5-1700', 1 ,1),
-- (39, '20240904-6-1800', 1 ,1),
-- (40, '20240904-10-1400', 1 ,1),
-- (41, '20240905-1-1500', 1 ,1),
-- (42, '20240905-2-1600', 0 ,1),
-- (43, '20240905-3-1700', 1 ,1),
-- (44, '20240905-4-1800', 1 ,1),
-- (45, '20240905-5-1900', 0 ,1),
-- (46, '20240905-6-2000', 0 ,1),
-- (47, '20240905-7-2100', 1 ,1),
-- (48, '20240905-8-1400', 0 ,1),
-- (49, '20240905-9-1500', 0 ,1),
-- (50, '20240905-10-1600', 1 ,0);



