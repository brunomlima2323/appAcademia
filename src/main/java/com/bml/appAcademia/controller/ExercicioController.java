package com.bml.appAcademia.controller;

import com.bml.appAcademia.domain.exercicio.Exercicio;
import com.bml.appAcademia.domain.exercicio.ExercicioDTO;
import com.bml.appAcademia.domain.exercicio.ExercicioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/exercicios")
public class ExercicioController {

    @Autowired
    private ExercicioRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid ExercicioDTO dados, UriComponentsBuilder uriBuilder){
        Exercicio exercicio = repository.save(new Exercicio(dados));
        ExercicioDTO exercicioDTO = new ExercicioDTO(exercicio);
        var uri = uriBuilder.path("/{id}").buildAndExpand(exercicio.getId()).toUri();
        return ResponseEntity.created(uri).body(exercicioDTO);
    }

    @GetMapping
    public ResponseEntity<Page<ExercicioDTO>> listar(@PageableDefault(size = 10, sort ={"nomeExercicio"}) Pageable paginacao){
        Page page = repository.findAll(paginacao).map(ExercicioDTO::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExercicioDTO> detalhar(@PathVariable Long id){
        try{
            Exercicio exercicio = repository.getReferenceById(id);
            return ResponseEntity.ok(new ExercicioDTO(exercicio));
        } catch (Exception e) {
            throw new EntityNotFoundException("Treino ID " +id+" não encontrado");
        }
    }

    @PutMapping
    @Transactional
    public ResponseEntity autalizar(@RequestBody @Valid ExercicioDTO dados){
        Exercicio exercicio = repository.getReferenceById(dados.id());
        exercicio.atualizarDados(dados);
        return ResponseEntity.ok(new ExercicioDTO(exercicio));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id){
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
