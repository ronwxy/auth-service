create schema if not exists auth collate latin1_swedish_ci;

create table if not exists oauth_client_details
(
	id int auto_increment
		primary key,
	resource_ids json null,
	authorized_grant_types varchar(100) null,
	scope varchar(100) null,
	web_server_redirect_uri json null,
	authorities json null,
	access_token_validity int null,
	refresh_token_validity int null,
	additional_information json null,
	client_secret varchar(200) null,
	client_id varchar(80) null,
	autoapprove json null
);

create table if not exists sys_group
(
	id int auto_increment
		primary key,
	name varchar(60) null,
	description varchar(255) null,
	is_show tinyint(1) null,
	show_weight int null,
	create_time datetime null,
	update_time datetime null
);

create table if not exists sys_job
(
	id int auto_increment
		primary key,
	name varchar(60) null,
	description varchar(255) null,
	parent_id int null,
	parent_ids varchar(255) null,
	is_show tinyint(1) null,
	show_weight int null
);

create table if not exists sys_organization
(
	id int auto_increment
		primary key,
	name varchar(60) null,
	parent_id int null,
	parent_ids varchar(255) null,
	type varchar(20) null,
	is_show tinyint(1) null,
	show_weight int null,
	create_time datetime null,
	update_time datetime null
);

create table if not exists sys_permission
(
	id int auto_increment
		primary key,
	name varchar(60) null,
	description varchar(255) null,
	permission varchar(255) null,
	is_show tinyint(1) null,
	show_weight int null,
	create_time datetime null,
	update_time datetime null
);

create table if not exists sys_resource
(
	id int auto_increment
		primary key,
	icon varchar(255) null,
	identity varchar(255) null,
	name varchar(60) null,
	parent_id int null,
	parent_ids varchar(255) null,
	url varchar(255) null,
	http_method varchar(10) null,
	is_show tinyint(1) null,
	show_weight int null,
	create_time datetime null,
	update_time datetime null
);

create table if not exists sys_role
(
	id int auto_increment
		primary key,
	name varchar(60) not null,
	description varchar(100) null,
	is_show tinyint(1) not null,
	show_weight int null,
	create_time datetime not null,
	update_time datetime null
);

create table if not exists sys_role_permission
(
	id int auto_increment
		primary key,
	role_id int null,
	resource_id int null,
	permission_ids varchar(255) null
);

create table if not exists sys_user
(
	id int auto_increment
		primary key,
	username varchar(60) not null,
	email varchar(60) null,
	phone varchar(20) null,
	type varchar(20) null,
	password varchar(60) null,
	salt varchar(60) null,
	channel varchar(20) null,
	status varchar(10) null,
	create_time datetime null,
	update_time datetime null,
	deleted tinyint(1) null
);

create table if not exists sys_user_2_openid
(
	id int auto_increment
		primary key,
	user_id int not null,
	openid_source varchar(32) not null,
	openid varchar(64) null,
	unionid varchar(64) null,
	appid varchar(64) null,
	create_time datetime null,
	bind_time datetime null
);

