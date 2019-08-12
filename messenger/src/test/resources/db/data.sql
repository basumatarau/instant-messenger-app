INSERT INTO instant_messenger_db_schema.users
(firstname, lastname, nickname, email, passwordhash, enabled)
VALUES('Vladimir', 'Putin', 'Vlad', 'bad@mail.ru', '$2a$10$0A1cuMZR0FpyaDoKOla1tufb9V7d/WVrbXhPqt08QNDSlJeCwXefm', true);

INSERT INTO instant_messenger_db_schema.users
(firstname, lastname, nickname, email, passwordhash, enabled)
VALUES('Barak', 'Obama', 'African-american', 'black@mail.gov', '$2a$10$0A1cuMZR0FpyaDoKOla1tufb9V7d/WVrbXhPqt08QNDSlJeCwXefm', true);

INSERT INTO instant_messenger_db_schema.users
(firstname, lastname, nickname, email, passwordhash, enabled)
VALUES('John', 'Doe', 'Billy', 'doe@mail.com', '$2a$10$0A1cuMZR0FpyaDoKOla1tufb9V7d/WVrbXhPqt08QNDSlJeCwXefm', true);

INSERT INTO instant_messenger_db_schema.chatrooms
("name", timecreated, public)
VALUES('nulla pari', 1000000000, true);

INSERT INTO instant_messenger_db_schema.chatrooms
("name", timecreated, public)
VALUES('nulla pariatur. Exce', 1000000000, false);

INSERT INTO instant_messenger_db_schema.chatrooms
("name", timecreated, public)
VALUES('Ut enim ad m', 1000000000, true);

INSERT INTO instant_messenger_db_schema.chatrooms
("name", timecreated, public)
VALUES('et dolore m', 1000000000, false);

INSERT INTO instant_messenger_db_schema.chatrooms
("name", timecreated, public)
VALUES('ut labore et dolore m', 1000000000, true);

INSERT INTO instant_messenger_db_schema.contact_entries
(entry_type, id_owner, id_person, confirmed, id_chatroom, enteredchat, enabled, privilege)
VALUES('cont', 1, 2, true, null, null, null, null);

INSERT INTO instant_messenger_db_schema.contact_entries
(entry_type, id_owner, id_person, confirmed, id_chatroom, enteredchat, enabled, privilege)
VALUES('cont', 2, 1, true, null, null, null, null);

INSERT INTO instant_messenger_db_schema.contact_entries
(entry_type, id_owner, id_person, confirmed, id_chatroom, enteredchat, enabled, privilege)
VALUES('subs', 1, NULL, false, 1, 1000001000, true, 'CHATADMIN');

INSERT INTO instant_messenger_db_schema.contact_entries
(entry_type, id_owner, id_person, confirmed, id_chatroom, enteredchat, enabled, privilege)
VALUES('subs', 2, NULL, false, 1, 1000002000, true, 'COMMONER');

INSERT INTO instant_messenger_db_schema.contact_entries
(entry_type, id_owner, id_person, confirmed, id_chatroom, enteredchat, enabled, privilege)
VALUES('subs', 3, NULL, false, 1, 1000003000, true, 'COMMONER');
