INSERT INTO banks(name)
VALUES ('Alfa');

INSERT INTO banks(name)
VALUES ('Belarus');

INSERT INTO commissionForClients(bankId, clientType, commission)
VALUES (1, 0, 0.01);

INSERT INTO commissionForClients(bankId, clientType, commission)
VALUES (1, 1, 0.02);

INSERT INTO commissionForClients(bankId, clientType, commission)
VALUES (2, 0, 0.015);

INSERT INTO commissionForClients(bankId, clientType, commission)
VALUES (2, 1, 0.025);

INSERT INTO exchangeRates(currency, rate)
VALUES (0, 2.59);

INSERT INTO exchangeRates(currency, rate)
VALUES (1, 2.9);

INSERT INTO exchangeRates(currency, rate)
VALUES (2, 1);

INSERT INTO users(login, password, roles, userState)
VALUES ('admin', '$2a$10$HWT5OgpemJUSD3KAzNTFTOz9AiS/QYg4BRJZowyEAMlTpIXqvkjMq', ARRAY ['ROLE_ADMIN', 'ROLE_OPERATOR'], 0);
