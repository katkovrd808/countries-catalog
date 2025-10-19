create extension if not exists "uuid-ossp";

create table if not exists "countries"
(
    id            UUID         unique not null default uuid_generate_v1() primary key,
    country_name  varchar(128) unique not null,
    iso_code      varchar(2)          not null check (
        char_length(iso_code) = 2
        and iso_code = upper(iso_code)
    ),
    coordinates   jsonb               not null
);

create index if not exists idx_countries_iso_code on countries (iso_code);