create table parent (
	id int auto_increment primary key,
	user_id int,
	first_name varchar(50),
	last_name varchar(50),
	email_address varchar(50) unique,
	foreign key(user_id) references user(id)
);
