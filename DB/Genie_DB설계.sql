use GominNo;
drop table if exists member;
drop table if exists Worry;
drop table if exists solution;
drop table if exists Favouring;

create table Member(
	memberId varchar(30) not null,
	password varchar(30) not null,
	usreName varchar(30) not null,
	Generation char(4) not null, 
	createDate Date,
	constraint primary key(memberId)
)default charset=utf8mb4;

create table Worry(
	worryId int auto_increment not null,
	writer varchar(30),
	worryTitle varchar(45),
	content text not null,
	categories varchar(30),
	answerGeneration char(4),
	regDate datetime,
	solved datetime,
	constraint primary key(worryId)
)default charset=utf8mb4;

create table Solution(
	solId int auto_increment not null,
	worryId int,
	solverId varchar(30),
	content text,
	regDate datetime,
	accepted datetime,
	favoured int,
	constraint primary key(solId)
)default charset=utf8mb4;

create table Favouring(
	solId int,
	whoFavour varchar(30),
	favourDate datetime,
	constraint primary key(solId, whofavour)
);
 

alter table member
	alter column createDate set default now(); 
	
alter table worry
	add constraint FK_member_worry
		foreign key(writer)
		references member(memberId)
		on update cascade
		on delete set null, 
	alter column answerGeneration set default '1090';

alter table Solution
	add constraint FK_worry_solution
		foreign key(worryId)
		references worry(worryId)
		on delete set null,
	add constraint FK_member_solution
		foreign key(solverId)
		references member(memberId)
		on delete set null;

alter table Favouring
	add constraint FK_Sol_Favour
		foreign key(solId)
		references solution(solId)
		on delete cascade,
	add constraint FK_Member_Favour
		foreign key(whoFavour)
		references member(memberId)
		on update cascade
		on delete cascade;

		
insert into member values('seomoon', 1234, 'injun Seo', '2030', now());
insert into member values('seostar', 1234, 'Frege', '2030', default);
insert into member values('skyblue', 1234, 'Russell', '3040', default);
insert into member values('eveningstar', 1234, '김연경', '3040', now());


insert into worry values(null, 'seomoon', 'job', 'not easy', null, '2030', now(), null);
insert into worry values(null, 'seostar', '연애 고민', '썸 타는 사람이랑', null, '2030', now(), null);

insert into solution values(null, 1, 'seostar', '매일 차근차근 준비하세요', now(), null, null);