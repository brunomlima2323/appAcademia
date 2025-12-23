package com.bml.appAcademia.domain.treinoExercicio;

import jakarta.validation.constraints.NotBlank;

public record TreinoExercicioDetalheDTO(Long id, Long treino_id, String nomeTreino, Long exercicio_id, String nomeExercicio) {

    public TreinoExercicioDetalheDTO(TreinoExercicio treinoExercicio){
        this(treinoExercicio.getId(), treinoExercicio.getTreino().getId(), treinoExercicio.getTreino().getNomeTreino(), treinoExercicio.getExercicio().getId(), treinoExercicio.getExercicio().getNomeExercicio() );
    }
}
