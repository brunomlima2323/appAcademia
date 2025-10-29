package com.bml.appAcademia.controller;

import com.bml.appAcademia.exercicio.ExercicioDTO;
import com.bml.appAcademia.exercicio.Exercicio;
import com.bml.appAcademia.exercicio.ExercicioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/exercicios")
public class ExercicioController {

    @Autowired
    private ExercicioRepository repository;

    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody @Valid ExercicioDTO dados){
        repository.save(new Exercicio(dados));
    }

    @GetMapping
    public Page<ExercicioDTO> listar(@PageableDefault(size = 10, sort ={"nomeExercicio"}) Pageable paginacao){
        return repository.findAll(paginacao).map(ExercicioDTO::new);
    }

    @PutMapping
    @Transactional
    public void autalizar(@RequestBody @Valid ExercicioDTO dados){
        Exercicio exercicio = repository.getReferenceById(dados.id());
        exercicio.atualizarDados(dados);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void excluir(@PathVariable Long id){
        repository.deleteById(id);
    }
}
