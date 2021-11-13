create table activityTracking (
	id int auto_increment primary key,
	child_id int,
	activity_id int,
	activity_timestamp timestamp default current_timestamp,
	activity_remark varchar(200),
	foreign key(child_id) references child(id),
	foreign key(activity_id) references activity(id)
);