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

drop table if exists teamboard;
create table teamboard (
    postid int auto_increment primary key,
    memberid int not null,
    teamcode int not null,
    content text not null,
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

drop table if exists gamepurchaselist;
create table gamepurchaselist (
    listid int auto_increment primary key,
    memberid int not null,
    purchasedate date not null default (current_date),
    foreign key (memberid) references members(memberid)
    on update cascade
    on delete cascade
);

drop table if exists gamepurchasedetails;
create table gamepurchasedetails (
    detailid int auto_increment primary key,
    listid int not null,
    matchid varchar(255),
    amount int not null,
    foreign key(listid) references gamepurchaselist(listid)
    on update cascade
    on delete cascade
);
