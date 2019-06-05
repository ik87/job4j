create table if not exists vacancy_sql_ru (
    id serial,
    name text unique,
    description text,
    link text,
    updated timestamp
);