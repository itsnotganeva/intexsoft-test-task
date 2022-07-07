create table users
(
    id bigSerial primary key,
    login varchar(50) not null,
    password varchar(500) not null,
    roles varchar[] not null,
    security_code varchar(6),
    user_state varchar(30) not null
);

CREATE TABLE clients
(
    id bigserial PRIMARY KEY,
    name varchar(255) not null,
    type varchar(20) not null,
    user_id bigSerial not null,
    foreign key (user_id) references users (id)
);

CREATE TABLE banks
(
    id bigserial PRIMARY KEY,
    name varchar(255) not null UNIQUE
);

CREATE TABLE bank_accounts
(
    id bigserial PRIMARY KEY,
    number int not null unique,
    currency varchar(10) not null,
    amount_of_money real,
    bank_id int not null,
    foreign key (bank_id) references banks (id),
    client_id int not null,
    foreign key (client_id) references clients (id)
);

create table transactions
(
    id bigserial PRIMARY KEY,
    amount_of_money real not null,
    sender_account_id int not null,
    receiver_account_id int not null,
    date timestamp not null,
    foreign key (sender_account_id) references bank_accounts (id),
    foreign key (receiver_account_id) references bank_accounts (id)
);

create table commission_for_clients
(
    id bigSerial primary key,
    bank_id bigserial not null,
    client_type int not null,
    commission real not null,
    foreign key (bank_id) references banks (id)
);


