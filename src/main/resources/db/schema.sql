-- DROP SCHEMA if exists instant_messenger_db_schema CASCADE;

CREATE SCHEMA if not exists instant_messenger_db_schema AUTHORIZATION postgres;

COMMENT ON SCHEMA instant_messenger_db_schema IS 'instant messenger web application - vironit training project ';

-- Drop table

-- DROP TABLE instant_messenger_db_schema.roles;

CREATE TABLE if not exists instant_messenger_db_schema.roles (
	id serial NOT NULL,
	"name" varchar(150) NOT NULL,
	CONSTRAINT roles_pk PRIMARY KEY (id)
);

-- DROP SEQUENCE instant_messenger_db_schema.users_id_seq;

CREATE SEQUENCE if not exists instant_messenger_db_schema.users_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;

-- Drop table

-- DROP TABLE instant_messenger_db_schema.users;

CREATE TABLE if not exists instant_messenger_db_schema.users (
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
CREATE UNIQUE INDEX if not exists users_email_idx ON instant_messenger_db_schema.users USING btree (email);
--ALTER SEQUENCE instant_messenger_db_schema.user_id_seq OWNED BY users.id;

-- Drop table

-- DROP TABLE instant_messenger_db_schema.chatroom_privileges;

CREATE TABLE if not exists instant_messenger_db_schema.chatroom_privileges (
	id serial NOT NULL,
	"name" varchar(100) NOT NULL,
	CONSTRAINT chatroom_privileges_pk PRIMARY KEY (id)
);

-- DROP SEQUENCE instant_messenger_db_schema.chatrooms_id_seq;

CREATE SEQUENCE if not exists instant_messenger_db_schema.chatrooms_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	CACHE 1
	NO CYCLE;

-- Drop table

-- DROP TABLE instant_messenger_db_schema.chatrooms;

CREATE TABLE if not exists instant_messenger_db_schema.chatrooms (
	id bigint NOT NULL DEFAULT nextval('instant_messenger_db_schema.chatrooms_id_seq'),
	"name" varchar(150) NOT NULL,
	timecreated timestamp NOT NULL,
	public bool NOT NULL,
	CONSTRAINT chatrooms_pk PRIMARY KEY (id)
);

--## ALTER SEQUENCE chatrooms_id_seq OWNED BY chatrooms.id;

-- DROP SEQUENCE instant_messenger_db_schema.contactEntries_id_seq;

CREATE SEQUENCE if not exists instant_messenger_db_schema.contactEntries_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	CACHE 1
	NO CYCLE;

-- Drop table

-- DROP TABLE instant_messenger_db_schema.contactEntries;

CREATE TABLE if not exists instant_messenger_db_schema.contactEntries (
	id bigint NOT NULL DEFAULT nextval('instant_messenger_db_schema.contactEntries_id_seq'),
	entryType varchar(45) NOT NULL,
	id_owner bigint NOT NULL,
	id_person bigint,
	confirmed bool DEFAULT false,
	id_chatroom bigint,
	enteredchat timestamp,
	enabled bool,
	id_userprivilege int4 DEFAULT 0,
	CONSTRAINT subscriptions_pk PRIMARY KEY (id),
	CONSTRAINT users_has_chatrooms_fk FOREIGN KEY (id_owner) REFERENCES instant_messenger_db_schema.users(id) ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT subscriptions_fk_1 FOREIGN KEY (id_person) REFERENCES instant_messenger_db_schema.users(id) ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT users_has_chatrooms_fk_1 FOREIGN KEY (id_chatroom) REFERENCES instant_messenger_db_schema.chatrooms(id) ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT subscriptions_fk FOREIGN KEY (id_userprivilege) REFERENCES instant_messenger_db_schema.chatroom_privileges(id) ON UPDATE CASCADE
);
CREATE INDEX if not exists contacts_id_owner_idx ON instant_messenger_db_schema.contactEntries USING btree (id_owner);
CREATE INDEX if not exists contacts_id_person_idx ON instant_messenger_db_schema.contactEntries USING btree (id_person, id_owner);

--## ALTER SEQUENCE contactEntries_id_seq OWNED BY contactEntries.id;

-- Drop table

-- DROP TABLE instant_messenger_db_schema.imagemessageresources;

CREATE TABLE if not exists instant_messenger_db_schema.imagemessageresources (
	id bigserial NOT NULL,
	"name" varchar(150) NOT NULL,
	imagebin bytea NOT NULL,
	width int4 NOT NULL,
	height int4 NOT NULL,
	CONSTRAINT imagemessageresources_pk PRIMARY KEY (id)
);

-- DROP SEQUENCE instant_messenger_db_schema.messages_id_seq;

CREATE SEQUENCE if not exists instant_messenger_db_schema.messages_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;

-- Drop table

-- DROP TABLE instant_messenger_db_schema.messages;

CREATE TABLE if not exists instant_messenger_db_schema.messages (
	id bigint NOT NULL DEFAULT nextval('instant_messenger_db_schema.messages_id_seq'),
	messagetype varchar(45) NOT NULL,
	body varchar(500) NOT NULL,
	timesent bigint NOT NULL,
	id_contact bigint NULL,
	id_author bigint NOT NULL,
	id_messageresource bigint NULL,
	id_chatroom bigint NULL,
	CONSTRAINT message_pk PRIMARY KEY (id),
	CONSTRAINT private_messages_fk FOREIGN KEY (id_contact) REFERENCES instant_messenger_db_schema.contactEntries(id) ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT private_messages_fk1 FOREIGN KEY (id_author) REFERENCES instant_messenger_db_schema.users(id) ON UPDATE CASCADE,
	CONSTRAINT private_messages_fk3 FOREIGN KEY (id_chatroom) REFERENCES instant_messenger_db_schema.chatrooms(id) ON UPDATE CASCADE ON DELETE CASCADE
);
CREATE INDEX if not exists message_timesent_idx ON instant_messenger_db_schema.messages USING btree (timesent);
CREATE INDEX if not exists private_messages_id_author_idx ON instant_messenger_db_schema.messages USING btree (id_author);
CREATE INDEX if not exists messages_id_contact_idx ON instant_messenger_db_schema.messages (id_contact);
CREATE INDEX if not exists messages_timesent_idx ON instant_messenger_db_schema.messages (timesent);

--## ALTER SEQUENCE messages_id_seq OWNED BY messages.id;

-- DROP SEQUENCE instant_messenger_db_schema.messageresources_id_seq;

CREATE SEQUENCE if not exists instant_messenger_db_schema.messageresources_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	CACHE 1
	NO CYCLE;

-- Drop table

-- DROP TABLE instant_messenger_db_schema.messageresources;

CREATE TABLE if not exists instant_messenger_db_schema.messageresources (
	id bigint NOT NULL DEFAULT nextval('instant_messenger_db_schema.messageresources_id_seq'),
	id_message bigint NOT NULL,
	"name" varchar(150) NOT NULL,
	CONSTRAINT messageresource_pk PRIMARY KEY (id),
	CONSTRAINT messageresource_fk FOREIGN KEY (id_message) REFERENCES instant_messenger_db_schema.messages(id) ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT messageresource_fk1 FOREIGN KEY (id) REFERENCES instant_messenger_db_schema.imagemessageresources(id) ON UPDATE CASCADE ON DELETE CASCADE
);

--## ALTER SEQUENCE messageresources_id_seq OWNED BY messageresources.id;


