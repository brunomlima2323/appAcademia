package com.bml.appAcademia.controller;

import com.bml.appAcademia.domain.ValidacaoException;
import com.bml.appAcademia.domain.exercicio.ExercicioDTO;
import com.bml.appAcademia.domain.treino.Treino;
import com.bml.appAcademia.domain.treino.TreinoDTO;
import com.bml.appAcademia.domain.treino.TreinoRepository;
import com.bml.appAcademia.domain.treinoExercicio.*;
import com.bml.appAcademia.domain.usuario.Usuario;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/treinoExercicio")
@SecurityRequirement(name = "bearer-key")
public class TreinoExercicioController {

    @Autowired
    TreinoExercicioService service;

    @Autowired
    private TreinoExercicioRepository repository;

    @Autowired
    private TreinoRepository treinoRepository;

    @GetMapping
    public ResponseEntity pegar(@RequestBody TreinoExercicioDTO dados){
        System.out.println("chegou aqui!");
//        TreinoExercicio treinoExercicio = repository.save(new TreinoExercicio(dados));
//        TreinoExercicioDTO treinoExercicioDTO = new TreinoDTO(treinoExercicio);
//        var uri = uriBuilder.path("/{id}").buildAndExpand(treino.getId()).toUri();
//        return ResponseEntity.created(uri).body(treinoExercicioDTO);
        return ResponseEntity.ok(new TreinoExercicioDTO(dados.id(), dados.treino_id(), dados.exercicio_id()));
    }

    @GetMapping("/treino/{treinoId}")
    public ResponseEntity< List<TreinoExercicioDetalheDTO> > listarTreinoExercicio(@PathVariable Long treinoId){

        System.out.println("Chegou aqui!");

        Usuario usuario = recuperaUsuarioLogado();

        Optional<Treino> treino = treinoRepository.findById(treinoId);

        if(treino.isEmpty()){
            throw new ValidacaoException("Não foi encontrado nenhum treino com esse id");
        }

        if(!usuario.getId().equals( treino.get().getUsuario().getId() ) ){
            throw new ValidacaoException("Esse treino não pertence ao seu usuário");
        }

        List<TreinoExercicio> treinoExercicios = repository.findByTreinoId(treinoId);

        if( treinoExercicios.isEmpty() ){
            throw new ValidacaoException("Não foram encontrados exercicios para esse treino");
        }

        List<TreinoExercicioDetalheDTO> treinoExercicioDetalheDTO = treinoExercicios.stream().map(TreinoExercicioDetalheDTO::new).collect(Collectors.toList());

        return ResponseEntity.ok(treinoExercicioDetalheDTO);
    }

    @PostMapping
    public ResponseEntity cadastrar(@RequestBody TreinoExercicioDTO dados){
        service.cadastrar(dados);
        return ResponseEntity.ok(new TreinoExercicioDTO(dados.id(), dados.treino_id(), dados.exercicio_id()));
    }

    private Usuario recuperaUsuarioLogado(){
        // Recupera o usuário autenticado do contexto de segurança
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = (Usuario) authentication.getPrincipal();
        return usuario;
    }
}
