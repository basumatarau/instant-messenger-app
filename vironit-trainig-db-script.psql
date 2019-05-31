DROP SCHEMA instant_messenger_db_schema CASCADE;

CREATE SCHEMA instant_messenger_db_schema AUTHORIZATION postgres;

COMMENT ON SCHEMA instant_messenger_db_schema IS 'instant messenger web application - vironit training project ';

-- Drop table

-- DROP TABLE instant_messenger_db_schema.roles;

CREATE TABLE instant_messenger_db_schema.roles (
	id serial NOT NULL,
	"name" varchar(150) NOT NULL,
	CONSTRAINT roles_pk PRIMARY KEY (id)
);

-- DROP SEQUENCE instant_messenger_db_schema.users_id_seq;

CREATE SEQUENCE instant_messenger_db_schema.users_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;

-- Drop table

-- DROP TABLE instant_messenger_db_schema.users;

CREATE TABLE instant_messenger_db_schema.users (
	id bigint NOT NULL DEFAULT nextval('instant_messenger_db_schema.users_id_seq'),
	firstname varchar(150) NULL,
	lastname varchar(150) NULL,
	nickname varchar(60) NOT NULL,
	email varchar(160) NOT NULL,
	passwordhash varchar(160) NOT NULL,
	enabled bool NOT NULL,
	id_role int4 NOT NULL,
	CONSTRAINT users_pk PRIMARY KEY (id),
	CONSTRAINT users_fk1 FOREIGN KEY (id_role) REFERENCES instant_messenger_db_schema.roles(id) ON UPDATE CASCADE
);
CREATE UNIQUE INDEX users_email_idx ON instant_messenger_db_schema.users USING btree (email);
--ALTER SEQUENCE instant_messenger_db_schema.user_id_seq OWNED BY users.id;

-- Drop table

-- DROP TABLE instant_messenger_db_schema.chatroom_privileges;

CREATE TABLE instant_messenger_db_schema.chatroom_privileges (
	id serial NOT NULL,
	"name" varchar(100) NOT NULL,
	CONSTRAINT chatroom_privileges_pk PRIMARY KEY (id)
);

-- DROP SEQUENCE instant_messenger_db_schema.chatrooms_id_seq;

CREATE SEQUENCE instant_messenger_db_schema.chatrooms_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	CACHE 1
	NO CYCLE;

-- Drop table

-- DROP TABLE instant_messenger_db_schema.chatrooms;

CREATE TABLE instant_messenger_db_schema.chatrooms (
	id bigint NOT NULL DEFAULT nextval('instant_messenger_db_schema.chatrooms_id_seq'),
	"name" varchar(150) NOT NULL,
	timecreated timestamp NOT NULL,
	public bool NOT NULL,
	CONSTRAINT chatrooms_pk PRIMARY KEY (id)
);

--## ALTER SEQUENCE chatrooms_id_seq OWNED BY chatrooms.id;

-- DROP SEQUENCE instant_messenger_db_schema.contacts_id_seq;

CREATE SEQUENCE instant_messenger_db_schema.contacts_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;

-- Drop table

-- DROP TABLE instant_messenger_db_schema.contacts;

CREATE TABLE instant_messenger_db_schema.contacts (
	id bigint NOT NULL DEFAULT nextval('instant_messenger_db_schema.contacts_id_seq'),
	id_owner int8 NOT NULL,
	id_person int8 NOT NULL,
	confirmed bool NOT NULL DEFAULT false,
	CONSTRAINT contacts_pk PRIMARY KEY (id),
	CONSTRAINT subscription_fk FOREIGN KEY (id_owner) REFERENCES instant_messenger_db_schema.users(id) ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT subscription_fk_1 FOREIGN KEY (id_person) REFERENCES instant_messenger_db_schema.users(id) ON UPDATE CASCADE ON DELETE CASCADE
);
CREATE INDEX contacts_id_owner_idx ON instant_messenger_db_schema.contacts USING btree (id_owner);
CREATE INDEX contacts_id_person_idx ON instant_messenger_db_schema.contacts USING btree (id_person, id_owner);

--## ALTER SEQUENCE contacts_id_seq OWNED BY contacts.id;

-- Drop table

-- DROP TABLE instant_messenger_db_schema.imagemessageresources;

CREATE TABLE instant_messenger_db_schema.imagemessageresources (
	id bigserial NOT NULL,
	"name" varchar(150) NOT NULL,
	imagebin bytea NOT NULL,
	width int4 NOT NULL,
	height int4 NOT NULL,
	CONSTRAINT imagemessageresources_pk PRIMARY KEY (id)
);

-- DROP SEQUENCE instant_messenger_db_schema.messages_id_seq;

CREATE SEQUENCE instant_messenger_db_schema.messages_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;

-- Drop table

-- DROP TABLE instant_messenger_db_schema.messages;

CREATE TABLE instant_messenger_db_schema.messages (
	id bigint NOT NULL DEFAULT nextval('instant_messenger_db_schema.messages_id_seq'),
	messagetype varchar(45) NOT NULL,
	body varchar(500) NOT NULL,
	timesent timestamp NOT NULL,
	id_contact int8 NULL,
	id_author int8 NOT NULL,
	id_messageresource int8 NULL,
	id_chatroom int8 NULL,
	CONSTRAINT message_pk PRIMARY KEY (id),
	CONSTRAINT private_messages_fk FOREIGN KEY (id_contact) REFERENCES instant_messenger_db_schema.contacts(id) ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT private_messages_fk1 FOREIGN KEY (id_author) REFERENCES instant_messenger_db_schema.users(id) ON UPDATE CASCADE,
	CONSTRAINT private_messages_fk3 FOREIGN KEY (id_chatroom) REFERENCES instant_messenger_db_schema.chatrooms(id) ON UPDATE CASCADE ON DELETE CASCADE
);
CREATE INDEX message_timesent_idx ON instant_messenger_db_schema.messages USING btree (timesent);
CREATE INDEX private_messages_id_author_idx ON instant_messenger_db_schema.messages USING btree (id_author);

--## ALTER SEQUENCE messages_id_seq OWNED BY messages.id;

-- DROP SEQUENCE instant_messenger_db_schema.subscribers_id_seq;

CREATE SEQUENCE instant_messenger_db_schema.subscribers_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	CACHE 1
	NO CYCLE;

-- Drop table

-- DROP TABLE instant_messenger_db_schema.subscribers;

CREATE TABLE instant_messenger_db_schema.subscribers (
	id bigint NOT NULL DEFAULT nextval('instant_messenger_db_schema.subscribers_id_seq'),
	id_user int8 NOT NULL,
	id_chatroom int8 NOT NULL,
	enteredchat timestamp NOT NULL,
	id_userprivilege int4 NOT NULL DEFAULT 0,
	CONSTRAINT subscribers_pk PRIMARY KEY (id),
	CONSTRAINT users_has_chatrooms_fk FOREIGN KEY (id_user) REFERENCES instant_messenger_db_schema.users(id) ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT users_has_chatrooms_fk_1 FOREIGN KEY (id_chatroom) REFERENCES instant_messenger_db_schema.chatrooms(id) ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT users_has_chatrooms_fk_3 FOREIGN KEY (id_chatroom) REFERENCES instant_messenger_db_schema.chatroom_privileges(id) ON UPDATE CASCADE ON DELETE SET DEFAULT
);

--## ALTER SEQUENCE subscribers_id_seq OWNED BY subscribers.id;

-- DROP SEQUENCE instant_messenger_db_schema.messageresources_id_seq;

CREATE SEQUENCE instant_messenger_db_schema.messageresources_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	CACHE 1
	NO CYCLE;

-- Drop table

-- DROP TABLE instant_messenger_db_schema.messageresources;

CREATE TABLE instant_messenger_db_schema.messageresources (
	id bigint NOT NULL DEFAULT nextval('instant_messenger_db_schema.messageresources_id_seq'),
	id_message int8 NOT NULL,
	"name" varchar(150) NOT NULL,
	CONSTRAINT messageresource_pk PRIMARY KEY (id),
	CONSTRAINT messageresource_fk FOREIGN KEY (id_message) REFERENCES instant_messenger_db_schema.messages(id) ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT messageresource_fk1 FOREIGN KEY (id) REFERENCES instant_messenger_db_schema.imagemessageresources(id) ON UPDATE CASCADE ON DELETE CASCADE
);

--## ALTER SEQUENCE messageresources_id_seq OWNED BY messageresources.id;


INSERT INTO instant_messenger_db_schema.roles
("name")
VALUES('ADMIN');
INSERT INTO instant_messenger_db_schema.roles
("name")
VALUES('USER');

INSERT INTO instant_messenger_db_schema.users
(firstname, lastname, nickname, email, passwordhash, enabled, id_role)
VALUES('Vasia', 'Pupkin', 'pup', 'pupkin@email.com', 'stub', true, 1);
INSERT INTO instant_messenger_db_schema.users
(firstname, lastname, nickname, email, passwordhash, enabled, id_role)
VALUES('Barak', 'Obama', 'persident', 'obama@gov.us', 'stub', true, 2);
INSERT INTO instant_messenger_db_schema.users
(firstname, lastname, nickname, email, passwordhash, enabled, id_role)
VALUES('Vladimir', 'Putin', 'badboy', 'bad@mail.ru', 'stub', true, 2);

INSERT INTO instant_messenger_db_schema.contacts
(id_owner, id_person, confirmed)
VALUES(1, 2, true);
INSERT INTO instant_messenger_db_schema.contacts
(id_owner, id_person, confirmed)
VALUES(1, 3, true);
INSERT INTO instant_messenger_db_schema.contacts
(id_owner, id_person, confirmed)
VALUES(2, 1, true);
INSERT INTO instant_messenger_db_schema.contacts
(id_owner, id_person, confirmed)
VALUES(3, 1, true);

INSERT INTO instant_messenger_db_schema.messages
(body, timesent, id_contact, id_author, messagetype, id_messageresource, id_chatroom)
VALUES('hello message', '2003-01-29 09:23:14.504', 1, 1, 'pm', NULL, NULL);
