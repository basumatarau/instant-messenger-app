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
VALUES('Vladimir', 'Putin', 'Vlad', 'bad@mail.ru', '036a6b0638ebe1a8b59b964a4477255f5490b6b9c9f9fbd7cc105062d0fa66e9cef42ea63a2037a5aac7abaa11dee6de647f5605a8022954498e4d41c32aa91a', true, 1);
