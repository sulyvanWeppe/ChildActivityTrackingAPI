create table child (
	id int auto_increment primary key,
	first_name varchar(50),
	last_name varchar(50),
	parent_1_id int,
	parent_2_id int,
	age int,
	foreign key(parent_1_id) references parent(id),
	foreign key(parent_2_id) references parent(id)
);
