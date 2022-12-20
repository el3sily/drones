CREATE TABLE drone(
id int primary key AUTO_INCREMENT,
serial_number varchar(100) not null,
model varchar (100) not null,
weight_limit double not null,
state varchar(20) not null,
battery_capacity double not null,
constraint uk_drone_serial unique (serial_number)
);