package com.bml.appAcademia.exercicio;

import jakarta.validation.constraints.NotBlank;

public record ExercicioDTO(Long id, @NotBlank String nomeExercicio) {

        public ExercicioDTO(Exercicio exercicio){
                this(exercicio.getId(), exercicio.getNomeExercicio());
        }
}
