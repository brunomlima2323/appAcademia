package com.bml.appAcademia.controller;

import com.bml.appAcademia.dtos.DadosCadastroExercicioDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exercicios")
public class ExercicioController {

    @PostMapping
    public void cadastrar(@RequestBody DadosCadastroExercicioDTO dados){
        System.out.println(dados);
    }
}
