CREATE TABLE medication(
id int primary key AUTO_INCREMENT,
name varchar(100) not null,
weight double not null,
code varchar(100) not null,
image longblob,
drone_id int,

constraint uk_medication_code unique (code),
constraint drone_fk foreign key (drone_id) references drone (id)
)