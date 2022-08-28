/*Before executing this script(populate.sql), make sure you have previously executed the pm.sql script*/
/*Run all the ALTER statements below in order to avoid foreign key violations in the later DML statements*/
ALTER SEQUENCE enrollments_id_seq RESTART WITH 1;
ALTER SEQUENCE epics_id_seq RESTART WITH 1;
ALTER SEQUENCE projects_id_seq RESTART WITH 1;
ALTER SEQUENCE remember_me_tokens_id_seq RESTART WITH 1;
ALTER SEQUENCE roles_id_seq RESTART WITH 1;
ALTER SEQUENCE sprints_id_seq RESTART WITH 1;
ALTER SEQUENCE tasks_id_seq RESTART WITH 1;
ALTER SEQUENCE user_stories_id_seq RESTART WITH 1;
ALTER SEQUENCE users_id_seq RESTART WITH 1;

insert into roles(title)
values
('Scrum Master'),
('Product Owner'),
('Team Member');

insert into users(last_name, first_name, email, password, role_id)
values
('Bolonyi', 'Andreea', 'andreea@yahoo.com', 'andreea', 3),
('Indries', 'George', 'george@yahoo.com', 'george', 3),
('Balogh', 'Luca', 'luca@yahoo.com', 'luca', 3),
('Bodea', 'Catalin', 'catalin@yahoo.com', 'catalin', 3),
('Lazar', 'Ana', 'ana@yahoo.com', 'ana', 3),
('Hendre', 'Cristina', 'cristina@yahoo.com', 'cristina', 3),
('Frunzescu', 'Vlad', 'vlad@yahoo.com', 'vlad', 3),
('Ghiorghe', 'Bianca', 'bianca@yahoo.com', 'bianca', 3),
('Dochitoiu', 'Eduardo', 'eduardo@yahoo.com', 'eduardo', 3),
('Campean', 'Catalina', 'catalina@yahoo.com', 'catalina', 3);

insert into projects(title)
values
('Proiect Colectiv');

insert into enrollments(user_id, project_id)
values
(1, 1),
(2, 1),
(3, 1),
(4, 1),
(5, 1),
(6, 1),
(7, 1),
(8, 1),
(9, 1),
(10, 1);

insert into epics(title, created, project_id)
values
('Demo 1', '2021-11-05', 1),
('Demo 2', '2021-11-19', 1);

insert into sprints(title, start_date, end_date, epic_id)
values
('IE Sprint 1', '2021-11-05', '2021-11-19', 1);

insert into user_stories(title, description, status, created, assigned_to_id, created_by_id, sprint_id, epic_id)
values
('US - Login', 'As an user, I want to be able to access my account, so that I can view my backlog and progress.', 'IN_PROGRESS', '2021-11-05', 4, 1, 1, 1),
('US - Architecture', 'Layered Architecture', 'DONE', '2021-11-05', 1, 2, 1, 1);

insert into tasks(title, description, created, assigned_to_id, created_by_id, user_story_id)
values
('Create and populate database', 'create database in pgadmin with name ProjectManagement; run only commands from pm.sql and then populate database with your own data',
 '2021-11-10', 2, 1, 2),
 ('Model', 'domain classes - User, Task, Role, RememberMeToken',
 '2021-11-10', 1, 2, 2);



