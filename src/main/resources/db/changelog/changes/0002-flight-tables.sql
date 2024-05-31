-- liquibase formatted sql

-- changeset liena:1

CREATE TABLE flight
(
    id            serial PRIMARY KEY,
    airport_from_id        int       not null,
    airport_to_id            int       not null,
    carrier       varchar   not null,
    departureTime datetime not null,
    arrivalTime   datetime not null,
    CONSTRAINT airport_from_id_fk FOREIGN KEY (airport_from_id) references airport (id);
CONSTRAINT airport_to_id_fk FOREIGN KEY (airport_to_id) references airport (id);
)

