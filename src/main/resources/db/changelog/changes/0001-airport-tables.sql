-- liquibase formatted sql

-- changeset liena:1

CREATE TABLE airport (
    id serial PRIMARY KEY,
    country varchar NOT NULL,
    city varchar not null,
    airport varchar unique not null

);
