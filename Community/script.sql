create table tblUser (
    id varchar2(30) primary key,
    name varchar2(30) not null,
    pw varchar2(30) not null,
    email varchar2(100) not null
);

create table tblBoard (
    seq number primary key,
    subject varchar2(1000) not null,
    content varchar2(2000) not null,
    regdate date default sysdate not null,
    id varchar2(30) not null
        constraint tblboard_id_fk references tblUser(id)
);

create sequence seqBoard;

insert into tblUser values ('hong','홍길동','1111','hong@gmail.com');
insert into tblUser values ('dog','강아지','1111','dog@gmail.com');
insert into tblUser values ('cat','고양이','1111','cat@gmail.com');

insert into tblBoard 
    values (seqBoard.nextVal, '게시판 입니다.', '내용입니다.',default,'hong');
    
commit;

select * from tblUser;
select * from tblBoard;