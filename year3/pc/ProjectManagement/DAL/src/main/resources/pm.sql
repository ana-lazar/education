/*Run all the DROP statements below in order to recreate all the tables*/

drop table remember_me_tokens;
drop table enrollments;
drop table tasks;
drop table user_stories;
drop table sprints;
drop table epics;
drop table projects;
drop table users;
drop table roles;


CREATE TABLE roles(
	id serial,
	title varchar(255) not null,
	CONSTRAINT pk_roles PRIMARY KEY(id)
);


CREATE TABLE remember_me_tokens(
	id serial,
	email varchar(255) not null unique,
	password varchar(255) not null,
	CONSTRAINT pk_remember_me_tokens PRIMARY KEY(id)
);


CREATE TABLE users(
	id serial,
	last_name varchar(255),
	first_name varchar(255),
	email varchar(255) not null unique,
	password varchar(255) not null,
	role_id int,
	CONSTRAINT fk_users_roles FOREIGN KEY(role_id) REFERENCES roles(id),
	CONSTRAINT pk_users PRIMARY KEY(id)
);


CREATE TABLE projects(
	id serial,
	title varchar(255) not null,
	CONSTRAINT pk_projects PRIMARY KEY(id)
);


CREATE TABLE enrollments(
	id serial,
	user_id int not null,
	project_id int not null,
	CONSTRAINT fk_enrollements_users FOREIGN KEY(user_id) REFERENCES users(id),
	CONSTRAINT fk_enrollements_projects FOREIGN KEY(project_id) REFERENCES projects(id),
	CONSTRAINT user_id_project_id_un UNIQUE (user_id, project_id),
	CONSTRAINT pk_enrollments PRIMARY KEY(id)
);


CREATE TABLE epics(
	id serial,
	title varchar(255) not null,
	created date not null,
	project_id int not null,
	CONSTRAINT fk_epics_projects FOREIGN KEY(project_id) REFERENCES projects(id),
	CONSTRAINT pk_epics PRIMARY KEY(id)
);


CREATE TABLE sprints(
	id serial,
	title varchar(255) not null,
	start_date date not null,
	end_date date not null,
	epic_id int not null,
	CONSTRAINT fk_sprints_epics FOREIGN KEY(epic_id) REFERENCES epics(id),
	CONSTRAINT pk_sprints PRIMARY KEY(id)
);


CREATE TABLE user_stories(
	id serial,
	title varchar(255) not null,
	description varchar(255) not null,
	status varchar(255) not null,
	created date not null,
	assigned_to_id int not null,
	created_by_id int not null,
	sprint_id int not null,
	epic_id int,
	CONSTRAINT fk_user_stories_users_assigned FOREIGN KEY(assigned_to_id) REFERENCES users(id),
	CONSTRAINT fk_user_stories_users_created FOREIGN KEY(created_by_id) REFERENCES users(id),
	CONSTRAINT fk_user_stories_sprints FOREIGN KEY(sprint_id) REFERENCES sprints(id),
	CONSTRAINT fk_user_stories_epics FOREIGN KEY(epic_id) REFERENCES epics(id),
	CONSTRAINT pk_user_stories PRIMARY KEY(id)
);


CREATE TABLE tasks(
	id serial,
	title varchar(255) not null,
	description varchar(255) not null,
	created DATE not null,
	assigned_to_id int not null,
	created_by_id int not null,
	user_story_id int not null,
	CONSTRAINT fk_tasks_users_assigned FOREIGN KEY(assigned_to_id) REFERENCES users(id),
	CONSTRAINT fk_tasks_users_created FOREIGN KEY(created_by_id) REFERENCES users(id),
	CONSTRAINT fk_tasks_user_stories FOREIGN KEY(user_story_id) REFERENCES user_stories(id),
	CONSTRAINT pk_tasks PRIMARY KEY(id)
);
