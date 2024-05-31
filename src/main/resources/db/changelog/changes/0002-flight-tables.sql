-- liquibase formatted sql

-- changeset liena:2

CREATE TABLE flight (
                        id serial PRIMARY KEY,
                        airport_from_id int NOT NULL,
                        airport_to_id int NOT NULL,
                        carrier varchar NOT NULL,
                        departure_time datetime NOT NULL,
                        arrival_time datetime NOT NULL,
                        CONSTRAINT airport_from_id_fk FOREIGN KEY (airport_from_id) REFERENCES airport (id),
                        CONSTRAINT airport_to_id_fk FOREIGN KEY (airport_to_id) REFERENCES airport (id)
);
