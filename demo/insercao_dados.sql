INSERT INTO Client (first_name, last_name, phone) VALUES
('João', 'Silva', '119876543'),
('Maria', 'Santos', '219876543'),
('Carlos', 'Oliveira', '319876543');

-- Inserção de dados fictícios na tabela Service (Serviço)
INSERT INTO Service (name, description, price) VALUES
('Corte de Cabelo', 'Corte de cabelo tradicional', 30.00),
('Barba', 'Aparação e modelagem de barba', 20.00),
('Manicure', 'Cuidados com as unhas das mãos', 25.00);

-- Inserção de dados fictícios na tabela Product (Produto)
INSERT INTO Product (name, description, quantity, price) VALUES
('Shampoo', 'Shampoo para cabelos normais', 50, 15.00),
('Condicionador', 'Condicionador para cabelos secos', 40, 18.00),
('Gel para Cabelo', 'Gel fixador para cabelos', 30, 12.00);

-- Inserção de dados fictícios na tabela Invoice (Fatura)
INSERT INTO Invoice (client_id, appointment_id,sub_total, reductions, tax, total) VALUES
(1, 1, 50.00, 0.00, 7.50, 57.50),
(2, 2, 45.00, 0.00, 6.75, 51.75),
(3, 3, 60.00, 0.00, 9.00, 69.00);

-- Inserção de dados fictícios na tabela Event (Evento)
INSERT INTO Event (date_time, client_id, invoice_id, service_id, description, type) VALUES
('2024-04-25 10:00:00', 1, 1, 1, 'Corte de cabelo agendado', 2),
('2024-04-26 14:00:00', 3, 2, 2, 'Aparação de barba', 2),
('2024-04-27 16:00:00', 2, 3, 3, 'Manicure completa', 0);

-- Inserção de dados fictícios na tabela BillProduct (Produtos da Fatura)
INSERT INTO BillProduct (invoice_id, product_id) VALUES
(1, 1), -- Shampoo na fatura 1
(1, 2), -- Condicionador na fatura 1
(2, 3), -- Gel para Cabelo na fatura 2
(3, 1); -- Shampoo na fatura 3

-- Inserção de dados fictícios na tabela BillService (Serviços da Fatura)
INSERT INTO BillService (invoice_id, service_id) VALUES
(1, 1), -- Corte de Cabelo na fatura 1
(2, 2), -- Barba na fatura 2
(3, 3); -- Manicure na fatura 3