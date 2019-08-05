INSERT INTO instant_messenger_db_schema.roles
("name")
VALUES('ADMIN');
INSERT INTO instant_messenger_db_schema.roles
("name")
VALUES('USER');

INSERT INTO instant_messenger_db_schema.chatroom_privileges
("name")
VALUES('CHATADMIN');
INSERT INTO instant_messenger_db_schema.chatroom_privileges
("name")
VALUES('COMMONER');

INSERT INTO instant_messenger_db_schema.users
(firstname, lastname, nickname, email, passwordhash, enabled, id_role)
VALUES('Vladimir', 'Putin', 'Vlad', 'bad@mail.ru', '$2a$10$0A1cuMZR0FpyaDoKOla1tufb9V7d/WVrbXhPqt08QNDSlJeCwXefm', true, 1);

INSERT INTO instant_messenger_db_schema.users
(firstname, lastname, nickname, email, passwordhash, enabled, id_role)
VALUES('Barak', 'Obama', 'African-american', 'black@mail.gov', '$2a$10$0A1cuMZR0FpyaDoKOla1tufb9V7d/WVrbXhPqt08QNDSlJeCwXefm', true, 1);

INSERT INTO instant_messenger_db_schema.users
(firstname, lastname, nickname, email, passwordhash, enabled, id_role)
VALUES('John', 'Doe', 'Billy', 'doe@mail.com', '$2a$10$0A1cuMZR0FpyaDoKOla1tufb9V7d/WVrbXhPqt08QNDSlJeCwXefm', true, 1);

INSERT INTO instant_messenger_db_schema.chatrooms
("name", timecreated, public)
VALUES('nulla pari', '2019-09-22 17:11:45.142', true);

INSERT INTO instant_messenger_db_schema.chatrooms
("name", timecreated, public)
VALUES('nulla pariatur. Exce', '2019-09-30 23:04:37.251', false);

INSERT INTO instant_messenger_db_schema.chatrooms
("name", timecreated, public)
VALUES('Ut enim ad m', '2020-07-13 19:00:27.310', true);

INSERT INTO instant_messenger_db_schema.chatrooms
("name", timecreated, public)
VALUES('et dolore m', '2019-04-14 13:00:48.903', false);

INSERT INTO instant_messenger_db_schema.chatrooms
("name", timecreated, public)
VALUES('ut labore et dolore m', '2019-02-16 03:34:15.290', true);

INSERT INTO instant_messenger_db_schema.contact_entries
(entry_type, id_owner, id_person, confirmed, id_chatroom, enteredchat, enabled, id_userprivilege)
VALUES('cont', 1, 2, true, null, null, null, null);

INSERT INTO instant_messenger_db_schema.contact_entries
(entry_type, id_owner, id_person, confirmed, id_chatroom, enteredchat, enabled, id_userprivilege)
VALUES('cont', 2, 1, true, null, null, null, null);

INSERT INTO instant_messenger_db_schema.contact_entries
(entry_type, id_owner, id_person, confirmed, id_chatroom, enteredchat, enabled, id_userprivilege)
VALUES('subs', 1, NULL, false, 1, '2019-09-22 17:11:45.000', true, 1);

INSERT INTO instant_messenger_db_schema.contact_entries
(entry_type, id_owner, id_person, confirmed, id_chatroom, enteredchat, enabled, id_userprivilege)
VALUES('subs', 2, NULL, false, 1, '2019-09-22 17:17:45.000', true, 2);

INSERT INTO instant_messenger_db_schema.contact_entries
(entry_type, id_owner, id_person, confirmed, id_chatroom, enteredchat, enabled, id_userprivilege)
VALUES('subs', 3, NULL, false, 1, '2019-09-22 17:18:45.000', true, 2);
