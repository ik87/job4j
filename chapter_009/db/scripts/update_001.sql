create table if not exists item (
    id serial,
    name varchar(50),
    description text,
    created timestamp
);