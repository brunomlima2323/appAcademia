package com.bml.appAcademia.controller;

import com.bml.appAcademia.domain.treinoExercicio.TreinoExercicioDTO;
import com.bml.appAcademia.domain.treinoExercicio.TreinoExercicioService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/treinoExercicio")
@SecurityRequirement(name = "bearer-key")
public class TreinoExercicioController {

    @Autowired
    TreinoExercicioService service;

    @GetMapping
    public ResponseEntity pegar(@RequestBody TreinoExercicioDTO dados){
        System.out.println("chegou aqui!");
//        TreinoExercicio treinoExercicio = repository.save(new TreinoExercicio(dados));
//        TreinoExercicioDTO treinoExercicioDTO = new TreinoDTO(treinoExercicio);
//        var uri = uriBuilder.path("/{id}").buildAndExpand(treino.getId()).toUri();
//        return ResponseEntity.created(uri).body(treinoExercicioDTO);
        return ResponseEntity.ok(new TreinoExercicioDTO(dados.id(), dados.treino_id(), dados.exercicio_id()));
    }

    @PostMapping
    public ResponseEntity cadastrar(@RequestBody TreinoExercicioDTO dados){
        service.cadastrar(dados);
        return ResponseEntity.ok(new TreinoExercicioDTO(dados.id(), dados.treino_id(), dados.exercicio_id()));
    }
}
