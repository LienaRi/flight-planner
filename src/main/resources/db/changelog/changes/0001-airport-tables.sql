-- liquibase formatted sql

-- changeset liena:1

CREATE TABLE airport
(
    airport_id varchar unique not null PRIMARY KEY,
    country varchar        NOT NULL,
    city    varchar        not null
);
