insert into roles (id, name) values (1, 'admin');
insert into roles (id, name) values (2, 'student');
insert into roles (id, name) values (3, 'visitor');

insert into users (id, creation_date, nickname, password) values (1, NOW(), 'admin', 'admin');
insert into users (id, creation_date, nickname, password) values (2, NOW(), 'student', 'student');

insert into users_roles (user_id, roles_id) values (1, 1);
insert into users_roles (user_id, roles_id) values (2, 2);
