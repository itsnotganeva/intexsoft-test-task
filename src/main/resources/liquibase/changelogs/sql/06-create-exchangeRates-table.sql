create table exchangeRates
(
    id bigSerial PRIMARY KEY,
    currency int not null UNIQUE,
    rate real not null
)