CREATE TABLE IF NOT EXISTS PRODUCT
(
    unique_id   VARCHAR(255)            NOT NULL PRIMARY KEY,
    sku         VARCHAR(255)            NOT NULL,
    title       VARCHAR(255)            NOT NULL,
    description LONGVARCHAR             NOT NULL,
    price       NUMERIC(20, 2)          NOT NULL,
    category    VARCHAR(255)            NOT NULL,
    created     TIMESTAMP default now() NOT NULL,
    updated     TIMESTAMP default now() NOT NULL
);
