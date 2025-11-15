CREATE extension IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS "countries"
(
    id            UUID         UNIQUE NOT NULL DEFAULT uuid_generate_v1() PRIMARY KEY,
    country_name  varchar(128) UNIQUE NOT NULL,
    iso_code      varchar(2)          NOT NULL CHECK (
        char_length(iso_code) BETWEEN 2 AND 3
        AND iso_code = upper(iso_code)
        AND iso_code ~ '^[A-Z]+$'
    ),
    coordinates   jsonb               NOT NULL CHECK (jsonb_typeof(coordinates) = 'array')
);

CREATE INDEX IF NOT EXISTS idx_countries_iso_code ON countries (iso_code);