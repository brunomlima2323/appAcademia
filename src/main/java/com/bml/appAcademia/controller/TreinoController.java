package com.bml.appAcademia.controller;

import com.bml.appAcademia.domain.exercicio.ExercicioDTO;
import com.bml.appAcademia.domain.treino.Treino;
import com.bml.appAcademia.domain.treino.TreinoDTO;
import com.bml.appAcademia.domain.treino.TreinoRepository;
import com.bml.appAcademia.domain.usuario.Usuario;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/treinos")
public class TreinoController {

    @Autowired
    private TreinoRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid TreinoDTO dados, UriComponentsBuilder uriBuilder){
        Treino treino = repository.save(new Treino(dados));
        TreinoDTO treinoDTO = new TreinoDTO(treino);
        var uri = uriBuilder.path("/{id}").buildAndExpand(treino.getId()).toUri();
        return ResponseEntity.created(uri).body(treinoDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity< List<TreinoDTO> > listarTreinosUsuario(@PathVariable Long id){
        // Recupera o usuário autenticado do contexto de segurança
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = (Usuario) authentication.getPrincipal();

        //Verifico se o usuário atenticado é o mesmo do id do parametro
        if(usuario.getId() != id){
            throw new EntityNotFoundException("Você só pode consultar sua lista de treinos");
        }

        List<TreinoDTO> treinos = repository.findByUsuarioId(id).stream().map(TreinoDTO::new).collect(Collectors.toList());
        if(treinos.isEmpty()){
            throw new EntityNotFoundException("Usuario ID " +id+" não encontrado ou não possui treinos");
        }
        return ResponseEntity.ok(treinos);
    }

}
