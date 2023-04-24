drop database if exists sgtour;

create database sgtour;

use sgtour;

create table users (
    user_id char(8) not null,
    username varchar(128) not null,
    password varchar(256) not null,
    email varchar(128) not null,
    primary key(user_id)
);

create table comments (
    comment_id char(8) not null,
    user_id char(8) not null,
    username varchar(128) not null,
    location_name varchar(128) not null,
    text varchar(128) not null,
    comment_date date not null,
    
    primary key(comment_id),
    constraint fk_user_id foreign key(user_id)
		references users(user_id)
    ON DELETE CASCADE
);

create table login_record(
    record_id int not null auto_increment,
    user_id char(8) not null,
    login_count int not null,
    last_login date not null,
    
    primary key(record_id),
    constraint fork_user_id foreign key(user_id)
		references users(user_id)
    ON DELETE CASCADE
);