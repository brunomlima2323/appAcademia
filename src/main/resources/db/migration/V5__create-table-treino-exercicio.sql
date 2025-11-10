CREATE TABLE treino_exercicio (
    id BIGSERIAL PRIMARY KEY,
    treino_id BIGINT NOT NULL,
    exercicio_id BIGINT NOT NULL,
    CONSTRAINT fk_treino_id FOREIGN KEY (treino_id) REFERENCES treinos (id),
    CONSTRAINT fk_exercicio_id FOREIGN KEY (exercicio_id) REFERENCES exercicios (id)
);