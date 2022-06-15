create table users
(
    id bigSerial primary key,
    login varchar(50) not null,
    password varchar(500) not null,
    roles varchar[] not null,
    securityCode varchar(6),
    userState varchar(30) not null
);

CREATE TABLE clients
(
    id bigserial PRIMARY KEY,
    name varchar(255) not null,
    type varchar(20) not null,
    userId bigSerial not null,
    foreign key (userId) references users (id)
);

CREATE TABLE banks
(
    id bigserial PRIMARY KEY,
    name varchar(255) not null UNIQUE
);

CREATE TABLE bankAccounts
(
    id bigserial PRIMARY KEY,
    number int not null unique,
    currency varchar(10) not null,
    amountOfMoney real,
    bankId int not null,
    foreign key (bankId) references banks (id),
    clientId int not null,
    foreign key (clientId) references clients (id)
);

create table transactions
(
    id bigserial PRIMARY KEY,
    amountOfMoney real not null,
    senderAccountId int not null,
    receiverAccountId int not null,
    date timestamp not null,
    foreign key (senderAccountId) references bankAccounts (id),
    foreign key (receiverAccountId) references bankAccounts (id)
);

create table commissionForClients
(
    id bigSerial primary key,
    bankId bigserial not null,
    clientType int not null,
    commission real not null,
    foreign key (bankId) references banks (id)
);


