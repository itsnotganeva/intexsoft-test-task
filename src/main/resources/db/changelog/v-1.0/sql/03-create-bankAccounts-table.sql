CREATE TABLE bankAccounts (
    id bigserial PRIMARY KEY,
    currency varchar(255) not null,
    amountOfMoney real,
    bankId int not null,
    foreign key (bankId) references banks(id),
    clientId int not null,
    foreign key (clientId) references clients(id)
)