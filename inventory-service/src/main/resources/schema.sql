CREATE TABLE IF NOT EXISTS INVENTORY
(
    unique_id VARCHAR(255)            NOT NULL PRIMARY KEY,
    stock     NUMERIC                 NOT NULL,
    created   TIMESTAMP default now() NOT NULL,
    updated   TIMESTAMP default now() NOT NULL
);
