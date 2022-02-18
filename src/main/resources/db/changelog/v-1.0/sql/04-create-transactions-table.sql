create table transactions
(
    id bigserial PRIMARY KEY,
    senderId int not null,
    receiverId int not null,
    amountOfMoney real not null,
    senderAccountId int not null,
    receiverAccountId int not null,
    date timestamp not null,
    foreign key (senderId) references clients (id),
    foreign key (receiverId) references clients (id),
    foreign key (senderAccountId) references bankAccounts (id),
    foreign key (receiverAccountId) references bankAccounts (id)
)