-- Adiciona a coluna de chave estrangeira na tabela treino
ALTER TABLE treinos
ADD COLUMN usuario_id BIGINT;

-- Cria a constraint de chave estrangeira
ALTER TABLE treinos
ADD CONSTRAINT fk_treino_usuario
FOREIGN KEY (usuario_id)
REFERENCES usuarios (id)
ON DELETE CASCADE;
