create table doctor (
	id int auto_increment primary key,
	user_id int,
	name varchar(50),
	email_address varchar(50) not null,
	phone_nr varchar(50),
	city varchar(50) not null,
	street varchar(50) not null,
	street_nr varchar(10) not null,
	zip_code varchar(10) not null,
	country varchar(50) not null,
	foreign key(user_id) references user(id)
);