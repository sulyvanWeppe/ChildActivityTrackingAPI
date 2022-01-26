create table activity (
	id int auto_increment primary key,
	name varchar(50) unique not null,
	measure_label varchar(50) not null
);