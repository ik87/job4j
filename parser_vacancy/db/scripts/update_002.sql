create table if not exists avito (
    id serial,
    name text unique,
    link text,
    updated timestamp,
    description text,
    price text
);