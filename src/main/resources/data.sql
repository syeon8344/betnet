-- 샘플
-- 팀
insert into Teams(TeamName) values('KIA');
insert into Teams(TeamName) values('삼성');
insert into Teams(TeamName) values('LG');
insert into Teams(TeamName) values('두산');
insert into Teams(TeamName) values('KT');
insert into Teams(TeamName) values('한화');
insert into Teams(TeamName) values('SSG');
insert into Teams(TeamName) values('롯데');
insert into Teams(TeamName) values('NC');
insert into Teams(TeamName) values('키움');
insert into Teams(TeamName ) values('자유');
insert into Teams(TeamName) values('없음');
-- 멤버
INSERT INTO Members (Username, Password, Name, Contact, Email, Gender, Age, TeamCode, Account) VALUES
('admin' , 'admin' , 'admin' , '010-123-4646' , 'admin@example.com' , 'M' , 20 , 1 , '123-123-123'),
('user001', 'password123', 'John Doe', '010-555-0001', 'johndoe@example.com', 'M', 28, 1, '123-456-789'),
('user002', 'password456', 'Jane Smith', '010-555-0002', 'janesmith@example.com', 'F', 34, 2, '987-654-321'),
('user003', 'password789', 'Michael Johnson', '010-555-0003', 'michaeljohnson@example.com', 'M', 22, 3, '456-789-123'),
('user004', 'password14561', 'Emily Davis', '010-555-0004', 'emilydavis@example.com', 'F', 29, 4, '321-654-987'),
('user005', 'password15612', 'Robert Brown', '010-555-0005', 'robertbrown@example.com', 'M', 31, 5, '654-321-987'),
('user006', 'password14515', 'Sarah Wilson', '010-555-0006', 'sarahwilson@example.com', 'F', 27, 6, '852-741-963'),
('user007', 'password1233215', 'James Lee', '010-555-0007', 'jamslee@example.com', 'M', 35, 7, '951-753-486'),
('user008', 'password1556', 'Laura White', '010-555-0008', 'laurawhite@example.com', 'F', 40, 8, '357-468-951'),
('user009', 'password1561', 'David Harris', '010-555-0009', 'davidharris@example.com', 'M', 23, 9, '468-951-357'),
('user010', 'password35412', 'Anna Martin', '010-555-0010', 'annamartin@example.com', 'F', 30, 10, '741-852-963'),
('user011', 'password11256', 'Daniel Robinson', '010-555-0011', 'danielrobinson@example.com', 'M', 29, 1, '852-963-741'),
('user012', 'password15451', 'Olivia Clark', '010-555-0012', 'oliviaclark@example.com', 'F', 24, 2, '963-741-852'),
('user013', 'password1234456', 'Matthew Lewis', '010-555-0013', 'matthewlewis@example.com', 'M', 32, 3, '741-963-852'),
('user014', 'password11565', 'Sophia Allen', '010-555-0014', 'sophiaallen@example.com', 'F', 27, 4, '852-741-963'),
('user015', 'password123215', 'Liam Young', '010-555-0015', 'liamyoung@example.com', 'M', 36, 5, '963-852-741'),
('user016', 'password122151', 'Ava King', '010-555-0016', 'avaking@example.com', 'F', 33, 6, '741-852-963'),
('user017', 'password5345', 'Ethan Wright', '010-555-0017', 'ethanwright@example.com', 'M', 26, 7, '852-963-741'),
('user018', 'password11321', 'Mia Scott', '010-555-0018', 'miascott@example.com', 'F', 28, 8, '963-741-852'),
('user019', 'password125613', 'Alexander Hill', '010-555-0019', 'alexanderhill@example.com', 'M', 31, 9, '741-963-852'),
('user020', 'password115136', 'Isabella Adams', '010-555-0020', 'isabellaadams@example.com', 'F', 25, 10, '852-741-963'),
('user021', 'password125451', 'Benjamin Baker', '010-555-0021', 'benjaminbaker@example.com', 'M', 30, 1, '963-852-741'),
('user022', 'password1513', 'Charlotte Nelson', '010-555-0022', 'charlottenelson@example.com', 'F', 29, 2, '741-963-852'),
('user023', 'password123133', 'William Carter', '010-555-0023', 'williamcarter@example.com', 'M', 34, 3, '852-963-741'),
('user024', 'password122133', 'Amelia Mitchell', '010-555-0024', 'ameliamitchell@example.com', 'F', 31, 4, '963-741-852'),
('user025', 'password123321', 'James Thompson', '010-555-0025', 'jamesthompson@example.com', 'M', 26, 5, '741-852-963'),
('user026', 'password123215', 'Harper Turner', '010-555-0026', 'harperturner@example.com', 'F', 32, 6, '852-963-741'),
('user027', 'password123878', 'Lucas Harris', '010-555-0027', 'lucasharris@example.com', 'M', 25, 7, '963-741-852'),
('user028', 'password122156', 'Ella Morris', '010-555-0028', 'ellamorris@example.com', 'F', 28, 8, '741-852-963'),
('user029', 'password124523', 'Noah Wilson', '010-555-0029', 'noahwilson@example.com', 'M', 27, 9, '852-963-741'),
('user030', 'password1235456', 'Lily Davis', '010-555-0030', 'lilydavis@example.com', 'F', 34, 10, '963-741-852'),
('user031', 'password125458', 'Elijah Thompson', '010-555-0031', 'elijahthompson@example.com', 'M', 22, 1, '741-852-963'),
('user032', 'password125454', 'Sofia Young', '010-555-0032', 'sofiayoung@example.com', 'F', 30, 2, '852-963-741'),
('user033', 'password125464', 'Mason Martinez', '010-555-0033', 'masonmartinez@example.com', 'M', 31, 3, '963-741-852'),
('user034', 'password12448', 'Mia Walker', '010-555-0034', 'miawalker@example.com', 'F', 26, 4, '741-852-963'),
('user035', 'password123548', 'Logan Hall', '010-555-0035', 'loganhall@example.com', 'M', 29, 5, '852-963-741'),
('user036', 'password124451', 'Avery Allen', '010-555-0036', 'averyallen@example.com', 'F', 27, 6, '963-741-852'),
('user037', 'password12451', 'Oliver King', '010-555-0037', 'oliverking@example.com', 'M', 34, 7, '741-852-963'),
('user038', 'password12354', 'Ella Green', '010-555-0038', 'ellagreen@example.com', 'F', 22, 8, '852-963-741'),
('user039', 'password123545', 'James Lee', '010-555-0039', 'jameslee@example.com', 'M', 28, 9, '963-741-852'),
('user040', 'password12556', 'Grace Robinson', '010-555-0040', 'gracerobinson@example.com', 'F', 32, 10, '741-852-963'),
('user041', 'password12215', 'Henry Davis', '010-555-0041', 'henrydavis@example.com', 'M', 25, 1, '852-963-741'),
('user042', 'password123818', 'Natalie Carter', '010-555-0042', 'nataliecarter@example.com', 'F', 31, 2, '963-741-852'),
('user043', 'password1232516', 'Samuel Evans', '010-555-0043', 'samuelevans@example.com', 'M', 28, 3, '741-852-963'),
('user044', 'password12358', 'Madison Phillips', '010-555-0044', 'madisonphillips@example.com', 'F', 30, 4, '852-963-741'),
('user045', 'password123546', 'Jacob Wilson', '010-555-0045', 'jacobwilson@example.com', 'M', 29, 5, '963-741-852'),
('user046', 'password12185', 'Chloe Mitchell', '010-555-0046', 'chloemitchell@example.com', 'F', 25, 6, '741-852-963'),
('user047', 'password12548', 'Isaac Moore', '010-555-0047', 'isaacmoore@example.com', 'M', 27, 7, '852-963-741'),
('user048', 'password12365', 'Zoe White', '010-555-0048', 'zoewhite@example.com', 'F', 30, 8, '963-741-852'),
('user049', 'password123348', 'Jack Harris', '010-555-0049', 'jackharris@example.com', 'M', 34, 9, '741-852-963'),
('user050', 'password1231231', 'Luna Young', '010-555-0050', 'lunayoung@example.com', 'F', 28, 10, '852-963-741');

-- 게시판
INSERT INTO TeamBoard (memberid, teamcode, content, title, views, likes) VALUES
(1, 1, '이것은 Team1의 첫 번째 게시물 내용입니다.', 'Team1 첫 번째 게시물', 10, 5),
(2, 2, 'Team2의 두 번째 게시물 내용을 보여줍니다.', 'Team2 두 번째 게시물', 15, 10),
(3, 3, 'Team3의 세 번째 게시물에 대한 자세한 내용입니다.', 'Team3 세 번째 게시물', 5, 2),
(4, 4, '여기 Team4의 네 번째 게시물 내용이 있습니다.', 'Team4 네 번째 게시물', 8, 4),
(5, 5, 'Team5의 다섯 번째 게시물에 대한 내용입니다.', 'Team5 다섯 번째 게시물', 20, 8),
(6, 6, 'Team6의 여섯 번째 게시물입니다.', 'Team6 여섯 번째 게시물', 12, 7),
(7, 7, 'Team7의 일곱 번째 게시물에 대한 흥미로운 내용입니다.', 'Team7 일곱 번째 게시물', 18, 12),
(8, 8, 'Team8의 여덟 번째 게시물 내용입니다.', 'Team8 여덟 번째 게시물', 6, 3),
(9, 9, 'Team9의 아홉 번째 게시물 내용입니다.', 'Team9 아홉 번째 게시물', 9, 5),
(10, 10, 'Team10의 열 번째 게시물에 대한 세부 사항입니다.', 'Team10 열 번째 게시물', 22, 10),
(11, 1, 'Team1의 열한 번째 게시물 내용입니다.', 'Team1 열한 번째 게시물', 14, 6),
(12, 2, 'Team2의 열두 번째 게시물 내용입니다.', 'Team2 열두 번째 게시물', 25, 15),
(13, 3, 'Team3의 열세 번째 게시물 내용입니다.', 'Team3 열세 번째 게시물', 7, 4),
(14, 4, 'Team4의 열네 번째 게시물 내용입니다.', 'Team4 열네 번째 게시물', 11, 8),
(15, 5, 'Team5의 열다섯 번째 게시물 내용입니다.', 'Team5 열다섯 번째 게시물', 24, 12),
(16, 6, 'Team6의 열여섯 번째 게시물 내용입니다.', 'Team6 열여섯 번째 게시물', 13, 9),
(17, 7, 'Team7의 열일곱 번째 게시물 내용입니다.', 'Team7 열일곱 번째 게시물', 19, 11),
(18, 8, 'Team8의 열여덟 번째 게시물 내용입니다.', 'Team8 열여덟 번째 게시물', 8, 5),
(19, 9, 'Team9의 열아홉 번째 게시물 내용입니다.', 'Team9 열아홉 번째 게시물', 10, 7),
(20, 10, 'Team10의 스무 번째 게시물 내용입니다.', 'Team10 스무 번째 게시물', 26, 14),
(21, 1, 'Team1의 스물한 번째 게시물 내용입니다.', 'Team1 스물한 번째 게시물', 15, 10),
(22, 2, 'Team2의 스물두 번째 게시물 내용입니다.', 'Team2 스물두 번째 게시물', 23, 13),
(23, 3, 'Team3의 스물세 번째 게시물 내용입니다.', 'Team3 스물세 번째 게시물', 9, 6),
(24, 4, 'Team4의 스물네 번째 게시물 내용입니다.', 'Team4 스물네 번째 게시물', 12, 8),
(25, 5, 'Team5의 스물다섯 번째 게시물 내용입니다.', 'Team5 스물다섯 번째 게시물', 27, 15),
(26, 6, 'Team6의 스물여섯 번째 게시물 내용입니다.', 'Team6 스물여섯 번째 게시물', 16, 11),
(27, 7, 'Team7의 스물일곱 번째 게시물 내용입니다.', 'Team7 스물일곱 번째 게시물', 21, 14),
(28, 8, 'Team8의 스물여덟 번째 게시물 내용입니다.', 'Team8 스물여덟 번째 게시물', 11, 7),
(29, 9, 'Team9의 스물아홉 번째 게시물 내용입니다.', 'Team9 스물아홉 번째 게시물', 13, 9),
(30, 10, 'Team10의 서른 번째 게시물 내용입니다.', 'Team10 서른 번째 게시물', 28, 16),
(31, 1, 'Team1의 서른한 번째 게시물 내용입니다.', 'Team1 서른한 번째 게시물', 18, 12),
(32, 2, 'Team2의 서른두 번째 게시물 내용입니다.', 'Team2 서른두 번째 게시물', 24, 17),
(33, 3, 'Team3의 서른세 번째 게시물 내용입니다.', 'Team3 서른세 번째 게시물', 10, 8),
(34, 4, 'Team4의 서른네 번째 게시물 내용입니다.', 'Team4 서른네 번째 게시물', 14, 10),
(35, 5, 'Team5의 서른다섯 번째 게시물 내용입니다.', 'Team5 서른다섯 번째 게시물', 29, 18),
(36, 6, 'Team6의 서른여섯 번째 게시물 내용입니다.', 'Team6 서른여섯 번째 게시물', 20, 13),
(37, 7, 'Team7의 서른일곱 번째 게시물 내용입니다.', 'Team7 서른일곱 번째 게시물', 24, 20),
(38, 8, 'Team8의 서른여덟 번째 게시물 내용입니다.', 'Team8 서른여덟 번째 게시물', 12, 9),
(39, 9, 'Team9의 서른아홉 번째 게시물 내용입니다.', 'Team9 서른아홉 번째 게시물', 17, 12),
(40, 10, 'Team10의 마흔 번째 게시물 내용입니다.', 'Team10 마흔 번째 게시물', 32, 22),
(41, 1, 'Team1의 마흔한 번째 게시물 내용입니다.', 'Team1 마흔한 번째 게시물', 22, 14),
(42, 2, 'Team2의 마흔두 번째 게시물 내용입니다.', 'Team2 마흔두 번째 게시물', 26, 18),
(43, 3, 'Team3의 마흔세 번째 게시물 내용입니다.', 'Team3 마흔세 번째 게시물', 14, 10),
(44, 4, 'Team4의 마흔네 번째 게시물 내용입니다.', 'Team4 마흔네 번째 게시물', 17, 12),
(45, 5, 'Team5의 마흔다섯 번째 게시물 내용입니다.', 'Team5 마흔다섯 번째 게시물', 32, 22),
(46, 6, 'Team6의 마흔여섯 번째 게시물 내용입니다.', 'Team6 마흔여섯 번째 게시물', 24, 16),
(47, 7, 'Team7의 마흔일곱 번째 게시물 내용입니다.', 'Team7 마흔일곱 번째 게시물', 28, 20),
(48, 8, 'Team8의 마흔여덟 번째 게시물 내용입니다.', 'Team8 마흔여덟 번째 게시물', 16, 12),
(49, 9, 'Team9의 마흔아홉 번째 게시물 내용입니다.', 'Team9 마흔아홉 번째 게시물', 19, 14),
(50, 10, 'Team10의 오십 번째 게시물 내용입니다.', 'Team10 오십 번째 게시물', 35, 25);
select * from teamboard;
-- 댓글
insert into comments (memberid, postid, commentindex, createdat, content) values
(1, 1, 1, CURRENT_DATE, '이 게시물에 대한 첫 번째 댓글입니다.'),
(2, 1, 2, CURRENT_DATE, '정말 유용한 정보네요. 감사합니다!'),
(3, 1, 3, CURRENT_DATE, '이 팀에 대한 더 많은 정보를 알고 싶습니다.'),
(4, 2, 1, CURRENT_DATE, 'Team2의 게시물이 좋습니다.'),
(5, 2, 2, CURRENT_DATE, '이 팀의 최근 성과가 궁금하네요.'),
(6, 2, 3, CURRENT_DATE, '팀2에 대한 자세한 분석이 필요합니다.'),
(7, 3, 1, CURRENT_DATE, 'Team3의 게시물에 대한 댓글입니다.'),
(8, 3, 2, CURRENT_DATE, '좋은 글이지만 좀 더 자세히 설명해주셨으면 좋겠어요.'),
(9, 3, 3, CURRENT_DATE, '이 팀의 전략에 대해 더 알고 싶습니다.'),
(10, 4, 1, CURRENT_DATE, 'Team4 게시물 내용이 유익합니다.'),
(11, 4, 2, CURRENT_DATE, '이 팀의 전반적인 상황을 알고 싶습니다.'),
(12, 4, 3, CURRENT_DATE, '다음 게시물에서 개선점을 알려주세요.'),
(13, 5, 1, CURRENT_DATE, 'Team5의 게시물이 흥미롭네요.'),
(14, 5, 2, CURRENT_DATE, '이 팀의 지난 성과를 리뷰해보는 것도 좋을 듯합니다.'),
(15, 5, 3, CURRENT_DATE, '팀5의 향후 계획에 대한 의견이 궁금합니다.'),
(16, 6, 1, CURRENT_DATE, 'Team6의 게시물 잘 읽었습니다.'),
(17, 6, 2, CURRENT_DATE, '이 팀의 최근 활동을 공유해주셔서 감사합니다.'),
(18, 6, 3, CURRENT_DATE, '추가적인 정보가 필요합니다.'),
(19, 7, 1, CURRENT_DATE, 'Team7에 대한 첫 번째 댓글입니다.'),
(20, 7, 2, CURRENT_DATE, '좋은 게시물이지만 몇 가지 의문이 있습니다.'),
(21, 7, 3, CURRENT_DATE, '이 팀의 비전과 목표가 궁금합니다.'),
(22, 8, 1, CURRENT_DATE, 'Team8의 게시물 내용이 흥미롭습니다.'),
(23, 8, 2, CURRENT_DATE, '이 팀에 대한 분석이 필요합니다.'),
(24, 8, 3, CURRENT_DATE, '더 많은 데이터와 사실이 포함되면 좋겠습니다.'),
(25, 9, 1, CURRENT_DATE, 'Team9의 게시물 잘 읽었습니다.'),
(26, 9, 2, CURRENT_DATE, '이 팀의 전략과 계획에 대한 자세한 정보가 필요합니다.'),
(27, 9, 3, CURRENT_DATE, '팀9의 성과를 어떻게 개선할 수 있을까요?'),
(28, 10, 1, CURRENT_DATE, 'Team10의 게시물 내용이 매우 유익합니다.'),
(29, 10, 2, CURRENT_DATE, '이 팀의 과거와 현재를 비교하는 것도 좋을 것 같습니다.'),
(30, 10, 3, CURRENT_DATE, 'Team10의 향후 계획에 대한 세부사항을 알고 싶습니다.'),
(31, 1, 4, CURRENT_DATE, '이 게시물에 대해 추가적으로 질문이 있습니다.'),
(32, 2, 4, CURRENT_DATE, '팀2의 최근 경기 결과가 궁금합니다.'),
(33, 3, 4, CURRENT_DATE, 'Team3의 전술에 대한 추가적인 정보가 필요합니다.'),
(34, 4, 4, CURRENT_DATE, '이 팀의 발전 가능성에 대해 논의해봅시다.'),
(35, 5, 4, CURRENT_DATE, '팀5의 새로운 전략이 기대됩니다.'),
(36, 6, 4, CURRENT_DATE, 'Team6의 최신 뉴스가 궁금합니다.'),
(37, 7, 4, CURRENT_DATE, '이 게시물의 주제에 대해 더 자세히 알고 싶습니다.'),
(38, 8, 4, CURRENT_DATE, 'Team8의 게시물에 대해 다른 의견이 있습니다.'),
(39, 9, 4, CURRENT_DATE, '팀9의 최근 성과를 분석해보는 것도 좋을 것 같습니다.'),
(40, 10, 4, CURRENT_DATE, '이 팀의 전략을 분석한 글이 기대됩니다.'),
(41, 1, 5, CURRENT_DATE, '게시물의 데이터 분석이 필요합니다.'),
(42, 2, 5, CURRENT_DATE, 'Team2의 지난 시즌 결과를 검토해보세요.'),
(43, 3, 5, CURRENT_DATE, '이 팀의 주요 선수들에 대해 더 알고 싶습니다.'),
(44, 4, 5, CURRENT_DATE, 'Team4의 성과 분석이 필요합니다.'),
(45, 5, 5, CURRENT_DATE, '팀5의 전술에 대한 상세 분석을 원합니다.'),
(46, 6, 5, CURRENT_DATE, '이 팀의 향후 계획에 대해 논의해보세요.'),
(47, 7, 5, CURRENT_DATE, 'Team7의 전술에 대한 의견이 궁금합니다.'),
(48, 8, 5, CURRENT_DATE, '팀8의 경기력에 대해 더 논의해봅시다.'),
(49, 9, 5, CURRENT_DATE, 'Team9의 향후 계획을 공유해주시면 좋겠습니다.'),
(50, 10, 5, CURRENT_DATE, '이 팀의 전반적인 성과를 평가해보는 것도 좋습니다.');

-- 포인트
-- 샘플 데이터 삽입 스크립트
INSERT INTO PointLogs (MemberID, PointChange, Description)
VALUES
-- 양수 포인트 변경 (Description = 1)
(1, 100, 1),
(2, 100000, 1),
(3, 120, 1),
(4, 180, 1),
(5, 110, 1),
(6, 200, 1),
(7, 130, 1),
(8, 140, 1),
(9, 160, 1),
(10, 170, 1),
(11, 110, 1),
(12, 150, 1),
(13, 140, 1),
(14, 190, 1),
(15, 10000, 1),
(16, 10000, 1),
(17, 180, 1),
(18, 200, 1),
(19, 130, 1),
(20, 150, 1),
(21, 140, 1),
(22, 170, 1),
(23, 190, 1),
(24, 110, 1),
(25, 160, 1),
(26, 130, 1),
(27, 200, 1),
(28, 150, 1),
(29, 120, 1),
(30, 180, 1),
(31, 190, 1),
(32, 140, 1),
(33, 170, 1),
(34, 110, 1),
(35, 200, 1),
(36, 130, 1),
(37, 150, 1),
(38, 160, 1),
(39, 180, 1),
(40, 120, 1),
(41, 190, 1),
(42, 150, 1),
(43, 140, 1),
(44, 170, 1),
(45, 200, 1),
(46, 130, 1),
(47, 180, 1),
(48, 160, 1),
(49, 110, 1),
(50, 150, 1),
-- 배당금 지급 (Description = 2)
(1, 200, 2),
(2, 180, 2),
(3, 150, 2),
(4, 170, 2),
(5, 160, 2),
(6, 190, 2),
(7, 120, 2),
(8, 130, 2),
(9, 200, 2),
(10, 150, 2),
(11, 180, 2),
(12, 170, 2),
(13, 160, 2),
(14, 200, 2),
(15, 130, 2),
(16, 190, 2),
(17, 120, 2),
(18, 150, 2),
(19, 200, 2),
(20, 170, 2),
(21, 160, 2),
(22, 180, 2),
(23, 130, 2),
(24, 200, 2),
(25, 190, 2),
(26, 150, 2),
(27, 170, 2),
(28, 120, 2),
(29, 160, 2),
(30, 200, 2),
(31, 180, 2),
(32, 130, 2),
(33, 190, 2),
(34, 160, 2),
(35, 150, 2),
(36, 200, 2),
(37, 170, 2),
(38, 120, 2),
(39, 180, 2),
(40, 130, 2),
(41, 190, 2),
(42, 160, 2),
(43, 200, 2),
(44, 150, 2),
(45, 170, 2),
(46, 120, 2),
(47, 200, 2),
(48, 190, 2),
(49, 160, 2),
(50, 130, 2),
-- 게임 구매 (Description = 3)
(1, -100, 3),
(2, -150, 3),
(3, -120, 3),
(4, -180, 3),
(5, -110, 3),
(6, -200, 3),
(7, -130, 3),
(8, -140, 3),
(9, -160, 3),
(10, -5000, 3),
(11, -110, 3),
(12, -150, 3),
(13, -140, 3),
(14, -190, 3),
(15, -5000, 3),
(16, -120, 3),
(17, -180, 3),
(18, -200, 3),
(19, -130, 3),
(20, -150, 3),
(21, -140, 3),
(22, -170, 3),
(23, -190, 3),
(24, -110, 3),
(25, -160, 3),
(26, -130, 3),
(27, -200, 3),
(28, -150, 3),
(29, -120, 3),
(30, -180, 3),
(31, -190, 3),
(32, -140, 3),
(33, -170, 3),
(34, -110, 3),
(35, -200, 3),
(36, -130, 3),
(37, -150, 3),
(38, -160, 3),
(39, -180, 3),
(40, -120, 3),
(41, -190, 3),
(42, -150, 3),
(43, -140, 3),
(44, -170, 3),
(45, -200, 3),
(46, -130, 3),
(47, -180, 3),
(48, -160, 3),
(49, -110, 3),
(50, -150, 3),
-- 포인트 출금 (Description = 4)
(1, -200, 4),
(2, -180, 4),
(3, -150, 4),
(4, -170, 4),
(5, -160, 4),
(6, -190, 4),
(7, -120, 4),
(8, -130, 4),
(9, -200, 4),
(10, -150, 4),
(11, -180, 4),
(12, -170, 4),
(13, -160, 4),
(14, -200, 4),
(15, -130, 4),
(16, -190, 4),
(17, -120, 4),
(18, -150, 4),
(19, -200, 4),
(20, -170, 4),
(21, -160, 4),
(22, -180, 4),
(23, -130, 4),
(24, -200, 4),
(25, -190, 4),
(26, -150, 4),
(27, -170, 4),
(28, -120, 4),
(29, -160, 4),
(30, -200, 4),
(31, -180, 4),
(32, -130, 4),
(33, -190, 4),
(34, -160, 4),
(35, -150, 4),
(36, -200, 4),
(37, -170, 4),
(38, -120, 4),
(39, -180, 4),
(40, -130, 4),
(41, -190, 4),
(42, -160, 4),
(43, -200, 4),
(44, -150, 4),
(45, -170, 4),
(46, -120, 4),
(47, -200, 4),
(48, -190, 4),
(49, -160, 4),
(50, -130, 4);
select sum(pointChange) as sum from pointlogs where memberid = 1 and Description = 3;
-- 게임구매목록
insert into gamepurchaselist (pointlogid) values
(101),
(102),
(103),
(104),
(105),
(106),
(107),
(108),
(109),
(110),
(111),
(112),
(113),
(114),
(115),
(116),
(117),
(118),
(119),
(120),
(121),
(122),
(123),
(124),
(125),
(126),
(127),
(128),
(129),
(130),
(131),
(132),
(133),
(134),
(135),
(136),
(137),
(138),
(139),
(140),
(141),
(142),
(143),
(144),
(145),
(146),
(147),
(148),
(149),
(150);

-- 게임구매상세
insert into gamepurchasedetails (listid, matchid, winandloss) VALUES
(1, '20240922-LG-1400', 1),
(2, '20240922-LG-1400', 1),
(3, '20240922-LG-1400', 1),
(4, '20240922-LG-1400', 1),
(4, '20240922-삼성-1400', 1),
(4, '20240922-KT-1400', 0),
(5, '20240922-삼성-1400', 0),
(6, '20240922-한화-1400', 1),
(6, '20240922-KT-1400', 0),
(6, '20240922-삼성-1400', 1),
(7, '20240922-한화-1400', 1),
(8, '20240922-KIA-1400', 1),
(8, '20240922-한화-1400', 1),
(9, '20240922-KIA-1400', 1),
(10, '20240924-두산-1830', 1),
(10, '20240924-SSG-1830', 0),
(10, '20240924-KT-1830', 1),
(10, '20240924-KIA-1830', 1),
(10, '20240924-키움-1830', 1),
(11, '20240924-키움-1830', 1),
(12, '20240924-키움-1830', 1),
(13, '20240924-키움-1830', 1),
(14, '20240924-키움-1830', 1),
(15, '20240927-롯데-1830', 1),
(15, '20240927-KT-1830', 1),
(15, '20240927-한화-1830', 1),
(16, '20240928-삼성-1700', 0),
(16, '20240928-롯데-1700', 0),
(16, '20240928-NC-1700', 0),
(16, '20240928-KT-1700', 1),
(17, '20240928-삼성-1700', 0),
(17, '20240928-롯데-1700', 1),
(17, '20240928-NC-1700', 0),
(17, '20240928-KT-1700', 1),
(18, '20241001-두산-1400', 0),
(18, '20241001-삼성-1400', 0),
(18, '20241001-삼성-1700', 0),
(19, '20241001-두산-1400', 1),
(19, '20241001-삼성-1400', 0),
(19, '20241001-삼성-1700', 0),
(20, '20240906-KIA-1830', 1),
(21, '20240907-LG-1400', 0),
(22, '20240907-롯데-1700', 0),
(23, '20240907-삼성-1700', 1),
(24, '20240907-KT-1700', 1),
(25, '20240907-KIA-1700', 0),
(26, '20240908-LG-1400', 1),
(27, '20240908-롯데-1400', 1),
(28, '20240908-삼성-1400', 0),
(29, '20240908-KIA-1400', 1),
(30, '20240910-LG-1830', 1),
(31, '20240910-SSG-1830', 0),
(32, '20240910-KT-1830', 1),
(33, '20240910-키움-1830', 1),
(34, '20240911-LG-1830', 0),
(35, '20240911-SSG-1830', 1),
(36, '20240911-KT-1830', 1),
(37, '20240911-한화-1830', 1),
(38, '20240912-LG-1830', 1),
(39, '20240912-KT-1830', 0),
(40, '20240912-KIA-1830', 1),
(41, '20240912-한화-1830', 1),
(42, '20240913-두산-1830', 1),
(43, '20240913-SSG-1830', 0),
(44, '20240913-롯데-1830', 1),
(45, '20240914-SSG-1400', 1),
(46, '20240914-롯데-1400', 1),
(47, '20240914-두산-1700', 0),
(48, '20240914-NC-1700', 1),
(49, '20240914-KIA-1700', 0),
(50, '20240915-SSG-1400', 1);

-- 회원 접속 로그
INSERT INTO access (memberid, memberdatetime) VALUES
(1, '2024-09-01 10:00:00'),
(2, '2024-09-01 11:15:00'),
(3, '2024-09-01 12:30:00'),
(4, '2024-09-02 09:30:00'),
(5, '2024-09-02 10:45:00'),
(6, '2024-09-02 11:00:00'),
(7, '2024-09-02 12:15:00'),
(8, '2024-09-03 08:20:00'),
(9, '2024-09-03 14:00:00'),
(10, '2024-09-03 15:30:00'),
(11, '2024-09-03 16:45:00'),
(12, '2024-09-03 17:00:00'),
(13, '2024-09-04 10:10:00'),
(14, '2024-09-04 11:20:00'),
(15, '2024-09-04 12:30:00'),
(16, '2024-09-04 13:40:00'),
(17, '2024-09-04 14:50:00'),
(18, '2024-09-05 09:15:00'),
(19, '2024-09-05 10:25:00'),
(20, '2024-09-05 11:35:00'),
(21, '2024-09-05 12:45:00'),
(22, '2024-09-05 13:55:00'),
(23, '2024-09-05 15:05:00'),
(24, '2024-09-06 10:00:00'),
(25, '2024-09-06 11:10:00'),
(26, '2024-09-06 12:20:00'),
(27, '2024-09-06 13:30:00'),
(28, '2024-09-06 14:40:00'),
(29, '2024-09-06 15:50:00'),
(30, '2024-09-07 09:00:00'),
(31, '2024-09-07 10:00:00'),
(32, '2024-09-07 11:00:00'),
(33, '2024-09-08 10:15:00'),
(34, '2024-09-08 11:25:00'),
(35, '2024-09-08 12:35:00'),
(36, '2024-09-08 13:45:00'),
(37, '2024-09-08 14:55:00'),
(38, '2024-09-08 16:05:00'),
(39, '2024-09-09 09:15:00'),
(40, '2024-09-09 10:25:00'),
(41, '2024-09-09 11:35:00'),
(42, '2024-09-10 10:00:00'),
(43, '2024-09-10 11:10:00'),
(44, '2024-09-10 12:20:00'),
(45, '2024-09-10 13:30:00'),
(46, '2024-09-10 14:40:00'),
(47, '2024-09-10 15:50:00'),
(48, '2024-09-10 16:00:00'),
(49, '2024-09-10 17:00:00'),
(50, '2024-09-10 18:00:00');

-- 굿즈거래 게시판 테이블 샘플
insert into market (mktitle, mkwriter, mkview, mkcontent) values
('게시글 제목 1', 2, 0, '게시글 내용 1입니다.'),
('게시글 제목 2', 3, 0, '게시글 내용 2입니다.'),
('게시글 제목 3', 4, 0, '게시글 내용 3입니다.'),
('게시글 제목 4', 5, 0, '게시글 내용 4입니다.'),
('게시글 제목 5', 6, 0, '게시글 내용 5입니다.'),
('게시글 제목 6', 7, 0, '게시글 내용 6입니다.'),
('게시글 제목 7', 8, 0, '게시글 내용 7입니다.'),
('게시글 제목 8', 9, 0, '게시글 내용 8입니다.'),
('게시글 제목 9', 10, 0, '게시글 내용 9입니다.'),
('게시글 제목 10', 11, 0, '게시글 내용 10입니다.'),
('게시글 제목 11', 12, 0, '게시글 내용 11입니다.'),
('게시글 제목 12', 13, 0, '게시글 내용 12입니다.'),
('게시글 제목 13', 14, 0, '게시글 내용 13입니다.'),
('게시글 제목 14', 15, 0, '게시글 내용 14입니다.'),
('게시글 제목 15', 16, 0, '게시글 내용 15입니다.'),
('게시글 제목 16', 17, 0, '게시글 내용 16입니다.'),
('게시글 제목 17', 18, 0, '게시글 내용 17입니다.'),
('게시글 제목 18', 19, 0, '게시글 내용 18입니다.'),
('게시글 제목 19', 20, 0, '게시글 내용 19입니다.'),
('게시글 제목 20', 21, 0, '게시글 내용 20입니다.'),
('게시글 제목 21', 22, 0, '게시글 내용 21입니다.'),
('게시글 제목 22', 23, 0, '게시글 내용 22입니다.'),
('게시글 제목 23', 24, 0, '게시글 내용 23입니다.'),
('게시글 제목 24', 25, 0, '게시글 내용 24입니다.'),
('게시글 제목 25', 26, 0, '게시글 내용 25입니다.'),
('게시글 제목 26', 27, 0, '게시글 내용 26입니다.'),
('게시글 제목 27', 28, 0, '게시글 내용 27입니다.'),
('게시글 제목 28', 29, 0, '게시글 내용 28입니다.'),
('게시글 제목 29', 30, 0, '게시글 내용 29입니다.'),
('게시글 제목 30', 31, 0, '게시글 내용 30입니다.'),
('게시글 제목 31', 32, 0, '게시글 내용 31입니다.'),
('게시글 제목 32', 33, 0, '게시글 내용 32입니다.'),
('게시글 제목 33', 34, 0, '게시글 내용 33입니다.'),
('게시글 제목 34', 35, 0, '게시글 내용 34입니다.'),
('게시글 제목 35', 36, 0, '게시글 내용 35입니다.'),
('게시글 제목 36', 37, 0, '게시글 내용 36입니다.'),
('게시글 제목 37', 38, 0, '게시글 내용 37입니다.'),
('게시글 제목 38', 39, 0, '게시글 내용 38입니다.'),
('게시글 제목 39', 40, 0, '게시글 내용 39입니다.'),
('게시글 제목 40', 41, 0, '게시글 내용 40입니다.'),
('게시글 제목 41', 42, 0, '게시글 내용 41입니다.'),
('게시글 제목 42', 43, 0, '게시글 내용 42입니다.'),
('게시글 제목 43', 44, 0, '게시글 내용 43입니다.'),
('게시글 제목 44', 45, 0, '게시글 내용 44입니다.'),
('게시글 제목 45', 46, 0, '게시글 내용 45입니다.'),
('게시글 제목 46', 47, 0, '게시글 내용 46입니다.'),
('게시글 제목 47', 48, 0, '게시글 내용 47입니다.'),
('게시글 제목 48', 49, 0, '게시글 내용 48입니다.'),
('게시글 제목 49', 50, 0, '게시글 내용 49입니다.'),
('게시글 제목 50', 1, 0, '게시글 내용 50입니다.');

-- 굿즈거래 댓글 테이블 샘플
insert into marketreply (mkreplywriter, mkid, mkreplycontent) values
(1, 1, '굿즈에 대한 첫 번째 댓글입니다!'),
(2, 1, '정말 멋진 제품이네요!'),
(3, 2, '배송이 빨랐어요. 감사합니다!'),
(1, 2, '재구매 의사 100%입니다.'),
(4, 3, '상태가 매우 좋습니다.'),
(5, 3, '이건 정말 추천해요!'),
(2, 4, '가격이 너무 비쌉니다.'),
(3, 4, '좋은 품질이에요!'),
(6, 5, '빠른 배송에 감동했습니다.'),
(1, 5, '생각보다 크기가 작네요.'),
(7, 6, '디자인이 마음에 들어요!'),
(8, 6, '친구에게 선물했어요.'),
(2, 7, '완전 만족합니다.'),
(9, 7, '다음에는 더 많은 수량 구매할게요.'),
(10, 8, '상세 설명과 같아요.'),
(11, 8, '리뷰를 보고 사서 후회 없어요.'),
(12, 9, '할인 기간이 너무 짧아요.'),
(13, 10, '상품이 잘 포장되어 왔어요.'),
(14, 11, '너무 귀엽습니다!'),
(15, 11, '선물용으로 좋네요.'),
(16, 12, '사이즈가 잘 맞습니다.'),
(17, 13, '여러 개 구매하고 싶어요.'),
(18, 14, '이 가격에 이 품질이라니!'),
(19, 15, '가족 모두가 좋아해요.'),
(20, 16, '재고가 빨리 소진되겠네요.'),
(21, 17, '의외로 품질이 좋아요.'),
(22, 18, '굿즈 팬이라 꼭 사야 해요!'),
(23, 19, '배송이 좀 늦었지만 괜찮아요.'),
(24, 20, '특별한 날에 맞춰 샀어요.'),
(25, 21, '너무 기분이 좋습니다.'),
(26, 22, '한정판이라서 꼭 가져야 해요.'),
(27, 23, '그냥 그랬어요.'),
(28, 24, '고급스러운 느낌이에요.'),
(29, 25, '사용할 때마다 기분이 좋아요.'),
(30, 26, '리뷰를 보고 믿고 구매했습니다.'),
(31, 27, '이 제품 강력 추천합니다!'),
(32, 28, '기대 이상이에요!'),
(33, 29, '어디서도 찾기 힘든 아이템이에요.'),
(34, 30, '전시용으로 구매했습니다.'),
(35, 31, '보관이 쉬워요.'),
(36, 32, '디자인이 너무 예뻐요.'),
(37, 33, '확실히 가성비 좋습니다.'),
(38, 34, '이건 정말 갖고 싶어요!'),
(39, 35, '기대했던 것보다 별로였어요.'),
(40, 36, '재미있는 상품입니다.'),
(41, 37, '하나 더 사고 싶네요!'),
(42, 38, '친구들이 다 부러워해요.'),
(43, 39, '상상했던 것과 달라서 아쉽습니다.'),
(44, 40, '또 구매할게요!'),
(45, 41, '너무 귀여운 제품입니다.'),
(46, 42, '여기서밖에 못 봤어요!'),
(47, 43, '후회하지 않을 선택이었습니다.'),
(48, 44, '정말 훌륭한 제품이에요.'),
(49, 45, '사고 싶어서 참기 힘드네요.'),
(50, 46, '사진보다 실물이 더 좋네요!'),
(1, 47, '이 제품 진짜 좋아요!'),
(2, 48, '여기에 있는 게 행복해요.'),
(3, 49, '품질 좋고 이 가격에 대만족!'),
(4, 50, '다른 제품도 구매할 계획이에요.');

-- 굿즈거래 거래신청 메시지 테이블 샘플
insert into marketmessage (msgmkid, msgsender, msgreceiver, msgstate) values
(1, 1, 2, false),
(2, 2, 3, false),
(3, 3, 4, true),
(4, 4, 5, false),
(5, 5, 6, true),
(6, 6, 7, false),
(7, 7, 8, true),
(8, 8, 9, false),
(9, 9, 10, true),
(10, 10, 11, false),
(11, 11, 12, true),
(12, 12, 13, false),
(13, 13, 14, true),
(14, 14, 15, false),
(15, 15, 16, true),
(16, 16, 17, false),
(17, 17, 18, true),
(18, 18, 19, false),
(19, 19, 20, true),
(20, 20, 21, false),
(21, 21, 22, true),
(22, 22, 23, false),
(23, 23, 24, true),
(24, 24, 25, false),
(25, 25, 26, true),
(26, 26, 27, false),
(27, 27, 28, true),
(28, 28, 29, false),
(29, 29, 30, true),
(30, 30, 31, false),
(31, 31, 32, true),
(32, 32, 33, false),
(33, 33, 34, true),
(34, 34, 35, false),
(35, 35, 36, true),
(36, 36, 37, false),
(37, 37, 38, true),
(38, 38, 39, false),
(39, 39, 40, true),
(40, 40, 41, false),
(41, 41, 42, true),
(42, 42, 43, false),
(43, 43, 44, true),
(44, 44, 45, false),
(45, 45, 46, true),
(46, 46, 47, false),
(47, 47, 48, true),
(48, 48, 49, false),
(49, 49, 50, true),
(50, 50, 1, false);

-- 게시글당 첨부파일 테이블
insert into marketfiles (mkid, filename) values
(1, 'image_1_1.jpg'),
(1, 'image_1_2.jpg'),
(2, 'image_2_1.jpg'),
(3, 'image_3_1.jpg'),
(3, 'image_3_2.jpg'),
(4, 'image_4_1.jpg'),
(5, 'image_5_1.jpg'),
(5, 'image_5_2.jpg'),
(6, 'image_6_1.jpg'),
(7, 'image_7_1.jpg'),
(8, 'image_8_1.jpg'),
(9, 'image_9_1.jpg'),
(9, 'image_9_2.jpg'),
(10, 'image_10_1.jpg'),
(10, 'image_10_2.jpg'),
(11, 'image_11_1.jpg'),
(12, 'image_12_1.jpg'),
(12, 'image_12_2.jpg'),
(13, 'image_13_1.jpg'),
(14, 'image_14_1.jpg'),
(15, 'image_15_1.jpg'),
(15, 'image_15_2.jpg'),
(16, 'image_16_1.jpg'),
(16, 'image_16_2.jpg'),
(17, 'image_17_1.jpg'),
(17, 'image_17_2.jpg'),
(18, 'image_18_1.jpg'),
(19, 'image_19_1.jpg'),
(20, 'image_20_1.jpg'),
(21, 'image_21_1.jpg'),
(21, 'image_21_2.jpg'),
(22, 'image_22_1.jpg'),
(23, 'image_23_1.jpg'),
(24, 'image_24_1.jpg'),
(25, 'image_25_1.jpg'),
(25, 'image_25_2.jpg'),
(26, 'image_26_1.jpg'),
(27, 'image_27_1.jpg'),
(27, 'image_27_2.jpg'),
(28, 'image_28_1.jpg'),
(29, 'image_29_1.jpg'),
(30, 'image_30_1.jpg'),
(31, 'image_31_1.jpg'),
(32, 'image_32_1.jpg'),
(33, 'image_33_1.jpg'),
(34, 'image_34_1.jpg'),
(35, 'image_35_1.jpg'),
(36, 'image_36_1.jpg'),
(37, 'image_37_1.jpg'),
(38, 'image_38_1.jpg'),
(39, 'image_39_1.jpg'),
(40, 'image_40_1.jpg'),
(41, 'image_41_1.jpg'),
(42, 'image_42_1.jpg'),
(42, 'image_42_2.jpg'),
(43, 'image_43_1.jpg'),
(44, 'image_44_1.jpg'),
(45, 'image_45_1.jpg'),
(46, 'image_46_1.jpg'),
(47, 'image_47_1.jpg'),
(48, 'image_48_1.jpg'),
(49, 'image_49_1.jpg'),
(49, 'image_49_2.jpg'),
(50, 'image_50_1.jpg'),
(50, 'image_50_2.jpg'),
(50, 'image_50_3.jpg');