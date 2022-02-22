CREATE TABLE clients
(
    id   bigserial PRIMARY KEY,
    name varchar(255) not null,
    type int          not null
)