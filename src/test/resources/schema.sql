drop table if exists activity_tracking;
drop table if exists activity;
drop table if exists child;
drop table if exists parent;
drop table if exists doctor;
drop table if exists user;

create table user (
	id int auto_increment primary key,
	email_address varchar(50) unique not null,
	login varchar(50) unique not null,
	password varchar(50) not null
);

create table parent (
	id int auto_increment primary key,
	user_id int,
	first_name varchar(50),
	last_name varchar(50),
	email_address varchar(50) unique,
	foreign key(user_id) references user(id)
);

create table child (
	id int auto_increment primary key,
	first_name varchar(50),
	last_name varchar(50),
	parent_1_id int,
	parent_2_id int,
	birth_date datetime(6),
	foreign key(parent_1_id) references parent(id),
	foreign key(parent_2_id) references parent(id)
);

create table activity (
	id int auto_increment primary key,
	name varchar(50) unique not null,
	measure_label varchar(50) not null
);

create table activity_tracking (
	id int auto_increment primary key,
	child_id int,
	activity_id int,
	activity_timestamp timestamp default current_timestamp,
	activity_remark varchar(200),
	foreign key(child_id) references child(id),
	foreign key(activity_id) references activity(id)
);

create table doctor (
	id int auto_increment primary key,
	user_id int,
	name varchar(50),
	email_address varchar(50) unique not null,
	phone_nr varchar(50),
	city varchar(50) not null,
	street varchar(50) not null,
	street_nr varchar(5) not null,
	zip_code varchar(10) not null,
	country varchar(50) not null,
	foreign key(user_id) references user(id)
);