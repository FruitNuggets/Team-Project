alter table PlayerInformation drop foreign key PlayerInformation_username_fk;

drop table PlayerInformation;
drop table ServerInformation;
drop table UserData; 

create table UserData(
username varchar(30),
password varchar(16));

create table PlayerInformation(
username varchar(30),
wins	 int(11));

create table ServerInformation(
serverip   varchar(30),
servername varchar(255));

alter table UserData
	add constraint UserData_username_pk primary key(username);
alter table ServerInformation
	add constraint ServerInformation_serverip_pk primary key(serverip);
alter table PlayerInformation
	add constraint PlayerInformation_username_fk foreign key(username)
    references UserData(username);
	
	
insert into UserData
	values('jsmith', aes_encrypt('hello123', 'key'));
insert into UserData
	values('msmith', aes_encrypt('pass123', 'key'));
insert into UserData
	values('tjones', aes_encrypt('123456', 'key'));
insert into UserData
	values('jjones', aes_encrypt('hello1234', 'key')); 

insert into PlayerInformation
	values('jsmith', 10);
insert into PlayerInformation
	values('msmith', 2);
insert into PlayerInformation
	values('tjones', 0);
insert into PlayerInformation
	values('jjones', 12);

insert into ServerInformation
	values('test name', aes_encrypt('123.123.123', 'key'));
insert into ServerInformation
	values('test name 2', aes_encrypt('321.321.321', 'key'));


	
