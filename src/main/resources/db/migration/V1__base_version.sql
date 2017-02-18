CREATE TABLE managers(
managerId SERIAL PRIMARY KEY,
email VARCHAR(40) UNIQUE,
passwordHash VARCHAR(120),
fullName VARCHAR(81),
company VARCHAR(40),
position VARCHAR(40),
photo BYTEA);

DROP TYPE IF EXISTS BOSS;
CREATE TYPE BOSS AS ENUM ('none','promoter','receiver');

CREATE TABLE contacts(
promoterId INT REFERENCES managers (managerId),
receiverId INT REFERENCES managers (managerId),
boss BOSS,
pending BOOL
);

CREATE TABLE events(
eventId SERIAL PRIMARY KEY,
dateTime TIMESTAMP,
text TEXT,
creatorId INT REFERENCES managers (managerId));

CREATE TABLE participants(
eventId INT REFERENCES events (eventId),
participantId INT REFERENCES managers (managerId));

