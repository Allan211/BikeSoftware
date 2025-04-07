CREATE DATABASE IF NOT EXISTS bike_shop;
USE bike_shop;

-- Tabela de usuários (administradores, vendedores, gestores de estoque)
CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    senha VARCHAR(100) NOT NULL,
    tipo_usuario ENUM('Administrador', 'Gestor de Estoque', 'Vendedor') NOT NULL
);

-- Tabela de bicicletas
CREATE TABLE bicicletas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    modelo VARCHAR(100) NOT NULL,
    marca VARCHAR(100),
    cor VARCHAR(50),
    tamanho VARCHAR(50),
    preco DOUBLE NOT NULL,
    disponibilidade BOOLEAN NOT NULL DEFAULT TRUE
);

-- Tabela de compradores
CREATE TABLE compradores (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    contato VARCHAR(100)
);

-- Tabela de vendas
CREATE TABLE vendas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    bicicleta_id INT,
    vendedor_id INT,
    comprador_id INT,
    data_venda DATETIME DEFAULT CURRENT_TIMESTAMP,
    forma_pagamento VARCHAR(50),
    desconto DOUBLE,
    FOREIGN KEY (bicicleta_id) REFERENCES bicicletas(id),
    FOREIGN KEY (vendedor_id) REFERENCES usuarios(id),
    FOREIGN KEY (comprador_id) REFERENCES compradores(id)
);

USE bike_shop;

INSERT INTO bicicletas (modelo, marca, cor, tamanho, preco, disponibilidade) VALUES
('Speedster 200', 'Scott', 'Preto', 'M', 4200.00, TRUE),
('Mountain Pro X', 'Caloi', 'Vermelho', 'G', 3500.00, TRUE),
('City Classic', 'Monark', 'Azul', 'P', 1500.00, TRUE),
('Road Elite', 'Specialized', 'Branco', 'M', 6200.00, TRUE),
('Trail Explorer', 'Trek', 'Verde', 'G', 4700.00, TRUE),
('Urban Move', 'Sense', 'Cinza', 'M', 2300.00, TRUE),
('Adventure Ride', 'Soul', 'Amarelo', 'G', 3900.00, TRUE),
('SpeedX Aero', 'Cannondale', 'Preto', 'P', 5100.00, TRUE),
('BikeFit Flex', 'Oggi', 'Rosa', 'M', 1800.00, TRUE),
('Cross Terrain', 'BMC', 'Laranja', 'G', 4500.00, TRUE);
-- Usuário administrador
INSERT INTO usuarios (nome, senha, tipo_usuario)
VALUES ('admin', 'admin123', 'Administrador');

-- Usuário gestor de estoque
INSERT INTO usuarios (nome, senha, tipo_usuario)
VALUES ('estoque', 'estoque123', 'Gestor de Estoque');

-- Usuário vendedor
INSERT INTO usuarios (nome, senha, tipo_usuario)
VALUES ('vendedor', 'vendedor123', 'Vendedor');
