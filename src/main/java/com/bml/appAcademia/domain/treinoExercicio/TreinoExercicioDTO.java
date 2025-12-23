package com.bml.appAcademia.domain.treinoExercicio;

import com.bml.appAcademia.domain.treino.Treino;
import jakarta.validation.constraints.NotBlank;

public record TreinoExercicioDTO(Long id, @NotBlank Long treino_id, @NotBlank Long exercicio_id) {

    public TreinoExercicioDTO(TreinoExercicio treinoExercicio){
        this(treinoExercicio.getId(), treinoExercicio.getTreino().getId(), treinoExercicio.getExercicio().getId() );
    }
}
