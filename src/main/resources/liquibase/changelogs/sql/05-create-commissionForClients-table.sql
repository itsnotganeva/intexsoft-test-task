create table commissionForClients
(
    id bigSerial primary key,
    bankId int not null,
    clientType int not null,
    commission real not null,
    foreign key (bankId) references banks (id)
)
