INSERT INTO banks(name)
VALUES ('Alfa');

INSERT INTO banks(name)
VALUES ('Belarus');

INSERT INTO commission_for_clients(bank_id, client_type, commission)
VALUES (1, 0, 0.01);

INSERT INTO commission_for_clients(bank_id, client_type, commission)
VALUES (1, 1, 0.02);

INSERT INTO commission_for_clients(bank_id, client_type, commission)
VALUES (2, 0, 0.015);

INSERT INTO commission_for_clients(bank_id, client_type, commission)
VALUES (2, 1, 0.025);

INSERT INTO users(login, password, roles, user_state)
VALUES ('admin', '$2a$10$HWT5OgpemJUSD3KAzNTFTOz9AiS/QYg4BRJZowyEAMlTpIXqvkjMq', ARRAY ['ROLE_ADMIN', 'ROLE_OPERATOR'], 'ACTIVATED');
