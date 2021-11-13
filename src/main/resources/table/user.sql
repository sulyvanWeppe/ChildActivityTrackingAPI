create table user (
	id int auto_increment primary key,
	email_address varchar(50) unique not null,
	login varchar(50) unique not null,
	password varchar(50) not null
);