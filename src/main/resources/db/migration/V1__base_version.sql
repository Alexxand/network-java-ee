CREATE TABLE managers(
id SERIAL PRIMARY KEY,
login VARCHAR(20) UNIQUE,
password VARCHAR(20),
name VARCHAR(20),
lastName VARCHAR(20),
company VARCHAR(20),
position VARCHAR(20),
photo BYTEA);

DROP TYPE IF EXISTS BOSS;
CREATE TYPE BOSS AS ENUM ('none','promoter','receiver');

CREATE TABLE contacts(
id_promoter INT REFERENCES managers (id),
id_receiver INT REFERENCES managers (id),
boss BOSS,
pending BOOL
);

CREATE TABLE messages(
id SERIAL PRIMARY KEY,
date TIMESTAMP,
text TEXT,
id_sender INT REFERENCES managers (id),
id_receiver INT REFERENCES managers (id));

CREATE TABLE events(
id SERIAL PRIMARY KEY,
date TIMESTAMP,
text TEXT,
id_creator INT REFERENCES managers (id));

CREATE TABLE event_participants(
id INT REFERENCES events (id),
id_participant INT REFERENCES managers (id));

