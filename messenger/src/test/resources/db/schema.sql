DROP SCHEMA if exists instant_messenger_db_schema CASCADE;

CREATE SCHEMA if not exists instant_messenger_db_schema AUTHORIZATION postgres;

COMMENT ON SCHEMA instant_messenger_db_schema IS 'instant messenger web application - vironit training project ';

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
	"role" varchar(8) NOT NULL DEFAULT 'USER',
	firstname varchar(150) NULL,
	lastname varchar(150) NULL,
	nickname varchar(60) NOT NULL,
	email varchar(160) NOT NULL,
	passwordhash varchar(160) NOT NULL,
	enabled bool NOT NULL,
	CONSTRAINT users_pk PRIMARY KEY (id)
);

CREATE UNIQUE INDEX if not exists users_email_idx ON instant_messenger_db_schema.users USING btree (email);

--ALTER SEQUENCE instant_messenger_db_schema.user_id_seq OWNED BY users.id;

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
	timecreated bigint NOT NULL,
	public bool NOT NULL,
	CONSTRAINT chatrooms_pk PRIMARY KEY (id)
);

--## ALTER SEQUENCE chatrooms_id_seq OWNED BY chatrooms.id;

-- DROP SEQUENCE instant_messenger_db_schema.contact_entries_id_seq;

CREATE SEQUENCE if not exists instant_messenger_db_schema.contact_entries_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	CACHE 1
	NO CYCLE;

-- Drop table

-- DROP TABLE instant_messenger_db_schema.contact_entries;

CREATE TABLE if not exists instant_messenger_db_schema.contact_entries (
	id bigint NOT NULL DEFAULT nextval('instant_messenger_db_schema.contact_entries_id_seq'),
	entry_type varchar(45) NOT NULL,
	id_owner bigint NOT NULL,
	id_person bigint,
	confirmed bool DEFAULT false,
	id_chatroom bigint,
	enteredchat bigint,
	enabled bool,
	privilege varchar(10),
	CONSTRAINT subscriptions_pk PRIMARY KEY (id),
	CONSTRAINT users_has_chatrooms_fk FOREIGN KEY (id_owner) REFERENCES instant_messenger_db_schema.users(id) ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT subscriptions_fk_1 FOREIGN KEY (id_person) REFERENCES instant_messenger_db_schema.users(id) ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT users_has_chatrooms_fk_1 FOREIGN KEY (id_chatroom) REFERENCES instant_messenger_db_schema.chatrooms(id) ON UPDATE CASCADE ON DELETE CASCADE
);
CREATE INDEX if not exists contacts_id_owner_idx ON instant_messenger_db_schema.contact_entries USING btree (id_owner);
CREATE INDEX if not exists contacts_id_person_idx ON instant_messenger_db_schema.contact_entries USING btree (id_person, id_owner);

--## ALTER SEQUENCE contact_entries_id_seq OWNED BY contact_entries.id;

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
	CONSTRAINT private_messages_fk FOREIGN KEY (id_contact) REFERENCES instant_messenger_db_schema.contact_entries(id) ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT private_messages_fk1 FOREIGN KEY (id_author) REFERENCES instant_messenger_db_schema.users(id) ON UPDATE CASCADE,
	CONSTRAINT private_messages_fk3 FOREIGN KEY (id_chatroom) REFERENCES instant_messenger_db_schema.chatrooms(id) ON UPDATE CASCADE ON DELETE CASCADE
);
CREATE INDEX if not exists message_timesent_idx ON instant_messenger_db_schema.messages USING btree (timesent);
CREATE INDEX if not exists private_messages_id_author_idx ON instant_messenger_db_schema.messages USING btree (id_author);
CREATE INDEX if not exists messages_id_contact_idx ON instant_messenger_db_schema.messages (id_contact);
CREATE INDEX if not exists messages_timesent_idx ON instant_messenger_db_schema.messages (timesent);

--## ALTER SEQUENCE messages_id_seq OWNED BY messages.id;

-- DROP SEQUENCE instant_messenger_db_schema.message_status_info_id_seq;

CREATE SEQUENCE instant_messenger_db_schema.message_status_info_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	CACHE 1
	NO CYCLE;

-- Drop table

-- DROP TABLE instant_messenger_db_schema.message_status_info;

CREATE TABLE instant_messenger_db_schema.message_status_info (
	id int8 NOT NULL DEFAULT nextval('instant_messenger_db_schema.message_status_info_id_seq'),
	id_contact_entry int8 NOT NULL,
	id_message int8 NOT NULL,
	delivered bool NOT NULL,
	time_delivered int8 NULL,
	"read" bool NOT NULL,
	time_read int8 NULL,
	CONSTRAINT message_status_info_pk PRIMARY KEY (id),
	CONSTRAINT message_status_info_fk FOREIGN KEY (id_message) REFERENCES instant_messenger_db_schema.messages(id) ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT message_status_info_fk_1 FOREIGN KEY (id_contact_entry) REFERENCES instant_messenger_db_schema.contact_entries(id) ON UPDATE CASCADE ON DELETE CASCADE
);
CREATE UNIQUE INDEX message_status_info_id_contact_entry_idx ON instant_messenger_db_schema.message_status_info USING btree (id_contact_entry, id_message);

--## ALTER SEQUENCE message_status_info_id_seq OWNED BY message_status_info.id;

-- DROP SEQUENCE instant_messenger_db_schema.messageresources_id_seq;

CREATE SEQUENCE if not exists instant_messenger_db_schema.message_resource_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	CACHE 1
	NO CYCLE;

-- Drop table

-- DROP TABLE instant_messenger_db_schema.message_resources;

CREATE TABLE if not exists instant_messenger_db_schema.message_resources (
	id bigint NOT NULL DEFAULT nextval('instant_messenger_db_schema.message_resource_id_seq'),
    resource_type varchar(10) NOT NULL,
	id_message bigint NOT NULL,
	"name" varchar(150) NOT NULL,
	binData bytea,
	width int4,
	height int4,
	CONSTRAINT message_resource_pk PRIMARY KEY (id),
	CONSTRAINT message_resource_fk FOREIGN KEY (id_message) REFERENCES instant_messenger_db_schema.messages(id) ON UPDATE CASCADE ON DELETE CASCADE
);

--## ALTER SEQUENCE message_resource_id_seq OWNED BY message_resources.id;


