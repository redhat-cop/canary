-- -TECHNICAL TABLES
CREATE TABLE parent_app (
 id serial,
 parent_app_name text NOT NULL,
 eai_code integer NOT NULL,
 in_scope boolean NOT NULL,
 PRIMARY KEY ( id )
);


CREATE TABLE server (
 id serial,
 hostname text NOT NULL UNIQUE,
 fqdn text NOT NULL,
 vmware_name text,
 domainName text ,
 mac_address text,
 sat_ipv4_addr text NOT NULL,
 sat_name text,
 dns_ipv4_addr text,
 rhel_version integer,
 services text,
 environments text,
 parent_app_id integer REFERENCES parent_app ( id ) NOT NULL,
 PRIMARY KEY (id )
);


--- ansible facts tables-------------------------------
CREATE TABLE ansible_facts (
 id serial,
 facts json,
 server_id integer REFERENCES server( id ) NOT NULL UNIQUE,
 PRIMARY KEY ( id )
);

CREATE TABLE ansible_services (
 id serial,
 services json,
 server_id integer REFERENCES server( id ) NOT NULL UNIQUE,
 PRIMARY KEY ( id )
);

CREATE TABLE ansible_packages (
 id serial,
 packages json,
 server_id integer REFERENCES server ( id ) NOT NULL UNIQUE,
 PRIMARY KEY ( id )
);

CREATE TABLE ansible_listeners(
 id serial,
 listeners json,
 server_id integer REFERENCES server ( id ) NOT NULL UNIQUE,
 PRIMARY KEY ( id )
);

CREATE TABLE ansible_repo_list (
 id serial,
 repo_list json,
 server_id integer REFERENCES server ( id ) NOT NULL UNIQUE,
 PRIMARY KEY ( id )
);

CREATE TABLE ansible_localusers_localgroups (
 id serial,
 local_users_groups json,
 server_id integer REFERENCES server ( id ) NOT NULL UNIQUE,
 PRIMARY KEY ( id )
);

CREATE TABLE ansible_processes (
 id serial,
 processes json,
 server_id integer REFERENCES server ( id ) NOT NULL UNIQUE,
 PRIMARY KEY (id )
);

CREATE TABLE ansible_cronjobs (
 id serial,
 cron_jobs json,
 dest_cron_jobs json,
 server_id integer REFERENCES server ( id ) NOT NULL UNIQUE,
 PRIMARY KEY ( id )
);

-------DESTIONATION DATA TABLES----
CREATE TABLE dest_facts (
 id serial,
 facts json,
 server_id integer REFERENCES server( id ) NOT NULL UNIQUE,
 PRIMARY KEY ( id )
);

CREATE TABLE dest_services (
 id serial,
 services json,
 server_id integer REFERENCES server( id ) NOT NULL UNIQUE,
 PRIMARY KEY ( id )
);

CREATE TABLE dest_packages (
 id serial,
 packages json,
 server_id integer REFERENCES server ( id ) NOT NULL UNIQUE,
 PRIMARY KEY ( id )
);

CREATE TABLE dest_listeners(
 id serial,
 listeners json,
 server_id integer REFERENCES server ( id ) NOT NULL UNIQUE,
 PRIMARY KEY ( id )
);

CREATE TABLE dest_repo_list (
 id serial,
 repo_list json,
 server_id integer REFERENCES server ( id ) NOT NULL UNIQUE,
 PRIMARY KEY ( id )
);

CREATE TABLE dest_localusers_localgroups (
 id serial,
 local_users_groups json,
 server_id integer REFERENCES server ( id ) NOT NULL UNIQUE,
 PRIMARY KEY ( id )
);

CREATE TABLE dest_processes (
 id serial,
 processes json,
 server_id integer REFERENCES server ( id ) NOT NULL UNIQUE,
 PRIMARY KEY (id )
);

CREATE TABLE dest_cronjobs (
 id serial,
 cron_jobs json,
 server_id integer REFERENCES server ( id ) NOT NULL UNIQUE,
 PRIMARY KEY ( id )
);

-----REPLACEMENT STATUS TABLE, remove the replace table, then delete the line

DROP TABLE IF EXISTS replace_status CASCADE;

----QUESTIONNAIRE TABLE CREATE AND QUESTION INSERTIONS
-- CREATE TABLE IF NOT EXISTS questionnaire(
--   id serial,
--   Category_Number    INTEGER  NOT NULL,
--   Category           TEXT NOT NULL,
--   Ref                INTEGER  NOT NULL,
--
--   Answering_Team     TEXT NOT NULL,
--   Attempt_to_Prefill VARCHAR(1) NOT NULL,
--   Question           TEXT NOT NULL,
--   Answer             TEXT,
--   Notes              TEXT,
--   parent_app_id INTEGER REFERENCES parent_app ( id ) NOT NULL,
--   PRIMARY KEY ( id, parent_app_id )
-- );


-----INSERT STATEMENTS FOR TESTING ---------
INSERT INTO parent_app(parent_app_name,eai_code,in_scope ) VALUES ('HELLO', '123', 't');
INSERT INTO parent_app(parent_app_name,eai_code,in_scope ) VALUES ('HELLO1', '1234', 't');
INSERT INTO parent_app(parent_app_name,eai_code,in_scope ) VALUES ('HELLO2', '12345', 't');

INSERT INTO server(hostname, fqdn,sat_ipv4_addr, sat_name, parent_app_id) VALUES ('lx1', 'lx1.foobar.com', '123.4', 'lx1', 1);
INSERT INTO server(hostname, fqdn,sat_ipv4_addr, sat_name, parent_app_id) VALUES ('lx2', 'lx2.foobar.com', '123.5', 'lx2', 1);
INSERT INTO server(hostname, fqdn,sat_ipv4_addr, sat_name, parent_app_id) VALUES ('lx3', 'lx3.foobar.com', '123.6', 'lx3', 2);
INSERT INTO server(hostname, fqdn,sat_ipv4_addr, sat_name, parent_app_id) VALUES ('lx4', 'lx4.foobar.com', '123.6', 'lx4', 2);
INSERT INTO server(hostname, fqdn,sat_ipv4_addr, sat_name, parent_app_id) VALUES ('lx5', 'lx5.foobar.com', '123.7', 'lx5', 3);

---ALTER SERVER TABLE - tested in DEV environment
ALTER TABLE server ADD COLUMN dest_hostname TEXT, ADD COLUMN dest_ipv4 TEXT, ADD COLUMN migration_status BOOLEAN;
