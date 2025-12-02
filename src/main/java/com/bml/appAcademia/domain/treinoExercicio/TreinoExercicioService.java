package com.bml.appAcademia.domain.treinoExercicio;

import com.bml.appAcademia.domain.ValidacaoException;
import com.bml.appAcademia.domain.exercicio.Exercicio;
import com.bml.appAcademia.domain.exercicio.ExercicioRepository;
import com.bml.appAcademia.domain.treino.Treino;
import com.bml.appAcademia.domain.treino.TreinoRepository;
import com.bml.appAcademia.domain.usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class TreinoExercicioService {

    @Autowired
    TreinoExercicioRepository repository;

    @Autowired
    TreinoRepository treinoRepository;

    @Autowired
    ExercicioRepository exercicioRepository;


    public void cadastrar(TreinoExercicioDTO dados){
        if(!treinoRepository.existsById(dados.treino_id())){
            throw new ValidacaoException("Id do treino informado não existe!");
        }

        if(!exercicioRepository.existsById(dados.exercicio_id())){
            throw new ValidacaoException("Id do exercicio informado não existe!");
        }

        var authentication = SecurityContextHolder.getContext().getAuthentication();
            Usuario usuario = (Usuario) authentication.getPrincipal();

        Treino treino = treinoRepository.findById(dados.treino_id()).get();

        if(treino.getUsuario().getId() != usuario.getId()){
            throw new ValidacaoException("Só é permitido cadastrar exercicios para os seus treinos!");
        }

        Exercicio exercicio = exercicioRepository.findById(dados.exercicio_id()).get();
        TreinoExercicio treinoExercicio = new TreinoExercicio(null, treino, exercicio);
        repository.save(treinoExercicio);
    }
}
