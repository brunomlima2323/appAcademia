package com.bml.appAcademia.domain.exercicio;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "exercicios")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Exercicio {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomeExercicio;

    public Exercicio(ExercicioDTO dados) {
        this.nomeExercicio = dados.nomeExercicio();
    }

    public void atualizarDados(@Valid ExercicioDTO dados) {
        if(dados.nomeExercicio() != null){
            this.nomeExercicio = dados.nomeExercicio();
        }
    }
}
