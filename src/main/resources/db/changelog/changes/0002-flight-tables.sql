-- liquibase formatted sql

-- changeset liena:2

CREATE TABLE flight
(
    id             serial PRIMARY KEY,
    airport_from   varchar  NOT NULL,
    airport_to     varchar  NOT NULL,
    carrier        varchar  NOT NULL,
    departure_time datetime NOT NULL,
    arrival_time   datetime NOT NULL,
    CONSTRAINT airport_from_fk FOREIGN KEY (airport_from) REFERENCES airport (airport_id),
    CONSTRAINT airport_to_fk FOREIGN KEY (airport_to) REFERENCES airport (airport_id)
);
