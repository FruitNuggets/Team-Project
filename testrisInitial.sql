drop table UserData; 

create table UserData(
username varchar(30),
password varchar(16));


alter table UserData
	add constraint UserData_username_pk primary key(username);
	
	
insert into UserData
	values('jsmith@uca.edu', aes_encrypt('hello123', 'key'));
insert into UserData
	values('msmith@uca.edu', aes_encrypt('pass123', 'key'));
insert into UserData
	values('tjones@yahoo.com', aes_encrypt('123456', 'key'));
insert into UserData
	values('jjones@yahoo.com', aes_encrypt('hello1234', 'key')); 

	
