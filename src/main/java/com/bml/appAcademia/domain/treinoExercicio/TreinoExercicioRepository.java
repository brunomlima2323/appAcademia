package com.bml.appAcademia.domain.treinoExercicio;

import com.bml.appAcademia.domain.treino.Treino;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TreinoExercicioRepository extends JpaRepository<TreinoExercicio,Long> {
    List<TreinoExercicio> findByTreinoId(Long id);
}
