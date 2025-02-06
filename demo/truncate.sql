﻿-- Desativar verificações de chave estrangeira temporariamente
SET FOREIGN_KEY_CHECKS = 0;

-- Limpar todas as tabelas
TRUNCATE TABLE billproduct;
TRUNCATE TABLE billservice;
TRUNCATE TABLE event;
TRUNCATE TABLE invoice;
TRUNCATE TABLE product;
TRUNCATE TABLE service;
TRUNCATE TABLE client;

-- Reativar verificações de chave estrangeira
SET FOREIGN_KEY_CHECKS = 1;SET FOREIGN_KEY_CHECKS = 1; 