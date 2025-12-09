package com.bml.appAcademia.controller;

import com.bml.appAcademia.domain.ValidacaoException;
import com.bml.appAcademia.domain.exercicio.ExercicioDTO;
import com.bml.appAcademia.domain.treino.Treino;
import com.bml.appAcademia.domain.treino.TreinoDTO;
import com.bml.appAcademia.domain.treino.TreinoRepository;
import com.bml.appAcademia.domain.usuario.DadosAutenticacaoDTO;
import com.bml.appAcademia.domain.usuario.Usuario;
import com.bml.appAcademia.domain.usuario.UsuarioRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@SecurityRequirement(name = "bearer-key")
public class TreinoController {

    @Autowired
    private TreinoRepository repository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid TreinoDTO dados, UriComponentsBuilder uriBuilder){
        Usuario usuario = recuperaUsuarioLogado();
        Integer treinoAtual = usuario.getTreinoAtualIndex();
        Treino treino = repository.save(new Treino(dados, usuario));
        TreinoDTO treinoDTO = new TreinoDTO(treino, treino.getOrdem().equals(treinoAtual));
        var uri = uriBuilder.path("/{id}").buildAndExpand(treino.getId()).toUri();
        return ResponseEntity.created(uri).body(treinoDTO);
    }

    @GetMapping()
    public ResponseEntity< List<TreinoDTO> > listarTreinosUsuario(){
        Usuario usuario = recuperaUsuarioLogado();

        List<Treino> treinos = repository.findByUsuarioIdOrderByOrdemAsc(usuario.getId());
        if(treinos.isEmpty()){
            throw new ValidacaoException("Usuario ID " +usuario.getId()+" não possui treinos");
        }

        Integer index = usuario.getTreinoAtualIndex();

        if (index == null || index < 0 || index >= treinos.size()) {
            throw new ValidacaoException("Treino atual inválido para o usuário");
        }

        List<TreinoDTO> treinosDTO = treinos.stream().map(t -> new TreinoDTO(t, t.getOrdem().equals(index))).collect(Collectors.toList());

        return ResponseEntity.ok(treinosDTO);
    }

    @PostMapping("/proximoTreino")
    @Transactional
    public ResponseEntity proximoTreinosUsuario(){
        Usuario usuario = recuperaUsuarioLogado();

        List<Treino> treinosOrdenados = repository.findByUsuarioIdOrderByOrdemAsc(usuario.getId());

        int total = treinosOrdenados.size();
        int atual = usuario.getTreinoAtualIndex();

        usuario.setTreinoAtualIndex((atual + 1) % total);

        usuarioRepository.save(usuario); // salva novo treino atual

        return ResponseEntity.ok().build();
    }

    private Usuario recuperaUsuarioLogado(){
        // Recupera o usuário autenticado do contexto de segurança
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = (Usuario) authentication.getPrincipal();
        return usuario;
    }

}
