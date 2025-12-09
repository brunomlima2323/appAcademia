-- Adiciona a coluna de chave estrangeira na tabela treino
ALTER TABLE treinos
ADD COLUMN ordem BIGINT;

ALTER TABLE usuarios
ADD COLUMN treino_atual_index BIGINT;
