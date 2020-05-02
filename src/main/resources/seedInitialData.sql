insert into roles (id, name) values (1, 'admin');
insert into roles (id, name) values (2, 'student');
insert into roles (id, name) values (3, 'visitor');

insert into users (id, creation_date, username, password) values (1, NOW(), 'admin', '$2y$12$ueNIYMbCoh3k7COcjaBequS/pepr3z3dp4zJEb54UvkC60mTy4IAu');
insert into users (id, creation_date, username, password) values (2, NOW(), 'student', '$2y$12$MQ774K6Pqbj69PYxHKQZWuGkeCld9/oEx1EOaij.rQPKKkWMNxyZy');

insert into users_roles (user_id, roles_id) values (1, 1);
insert into users_roles (user_id, roles_id) values (2, 2);

insert into exams (id, name, created_at) values (10, 'Parówka kartkówka', STR_TO_DATE('2020-05-01 21:28:58.00000', '%Y-%m-%d %H:%i:%s.%f'));
insert into exams (id, name, created_at) values (11, 'Kartkówka parówka', STR_TO_DATE('2020-05-01 21:28:58.10000', '%Y-%m-%d %H:%i:%s.%f'));

insert into questions (id, exam_id, seconds_for_answer, question) values (13, 10, 60, 'Dlaczego foton nie został wpuszczony na imprezę?');
insert into questions (id, exam_id, seconds_for_answer, question) values (14, 10, 60, 'Dlaczego programistka płacze?');
insert into questions (id, exam_id, seconds_for_answer, question) values (15, 11, 60, 'Czy szfank?');
insert into questions (id, exam_id, seconds_for_answer, question) values (16, 11, 60, 'Czy pank?');

insert into answers (id, question_id, valid, answer) values (23, 13, TRUE, 'Bo impreza była masowa');
insert into answers (id, question_id, valid, answer) values (24, 13, FALSE, 'Bo nie ma pralki');
insert into answers (id, question_id, valid, answer) values (25, 14, TRUE, 'Bo pralka się jej zepsuła');
insert into answers (id, question_id, valid, answer) values (26, 14, FALSE, 'Bo rozlała się szklana mleka');
insert into answers (id, question_id, valid, answer) values (27, 15, TRUE, 'Tak');
insert into answers (id, question_id, valid, answer) values (28, 15, FALSE, 'Nie');
insert into answers (id, question_id, valid, answer) values (29, 16, TRUE, 'Nie');
insert into answers (id, question_id, valid, answer) values (30, 16, FALSE, 'Tak');
