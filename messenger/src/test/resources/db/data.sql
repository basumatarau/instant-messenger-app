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
VALUES('Barak', 'Obama', 'Nigger', 'black@mail.gov', '$2a$10$0A1cuMZR0FpyaDoKOla1tufb9V7d/WVrbXhPqt08QNDSlJeCwXefm', true, 1);

INSERT INTO instant_messenger_db_schema.contact_entries
(entry_type, id_owner, id_person, confirmed, id_chatroom, enteredchat, enabled, id_userprivilege)
VALUES('cont', 1, 2, true, null, null, null, null);

INSERT INTO instant_messenger_db_schema.contact_entries
(entry_type, id_owner, id_person, confirmed, id_chatroom, enteredchat, enabled, id_userprivilege)
VALUES('cont', 2, 1, true, null, null, null, null);
