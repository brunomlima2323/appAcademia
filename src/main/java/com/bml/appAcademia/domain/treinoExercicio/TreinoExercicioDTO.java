package com.bml.appAcademia.domain.treinoExercicio;

import jakarta.validation.constraints.NotBlank;

public record TreinoExercicioDTO(Long id, @NotBlank Long treino_id, @NotBlank Long exercicio_id) {
}
