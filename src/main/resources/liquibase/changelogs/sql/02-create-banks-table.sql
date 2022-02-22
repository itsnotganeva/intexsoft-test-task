CREATE TABLE banks
(
    id   bigserial PRIMARY KEY,
    name varchar(255) not null UNIQUE
)