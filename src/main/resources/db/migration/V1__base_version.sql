CREATE TABLE managers(
managerId SERIAL PRIMARY KEY,
email VARCHAR(20) UNIQUE,
passwordHash VARCHAR(20),
fullName VARCHAR(20),
company VARCHAR(20),
position VARCHAR(20),
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

