create table vacancy_sql_ru (
    id serial,
    name text unique,
    description text,
    link text,
    updated timestamp
);

create table last_update (
    updated timestamp
);