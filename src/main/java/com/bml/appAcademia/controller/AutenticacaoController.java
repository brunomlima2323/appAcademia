package com.bml.appAcademia.controller;

import com.bml.appAcademia.domain.usuario.AutenticacaoService;
import com.bml.appAcademia.domain.usuario.DadosAutenticacaoDTO;
import com.bml.appAcademia.domain.usuario.Usuario;
import com.bml.appAcademia.domain.usuario.UsuarioRepository;
import com.bml.appAcademia.infra.security.DadosTokenJwtDTO;
import com.bml.appAcademia.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    public ResponseEntity efetuarLogin(@RequestBody DadosAutenticacaoDTO dados){
        System.out.println("CHEGOU NO CONTROLLER DE LOGIN");
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha()); //cria um objeto UsernamePasswordAuthenticationToken com esses dados
        Authentication authentication = authenticationManager.authenticate(authenticationToken); //Esse token é enviado ao AuthenticationManager, que dispara a cadeia de autenticação interna do Spring Security.
        Usuario usuario = (Usuario) authentication.getPrincipal();
        System.out.println(usuario.getId());
        String tokenJWT = tokenService.gerarToken( usuario );
        return ResponseEntity.ok(new DadosTokenJwtDTO(tokenJWT, usuario.getId()));
    }

    @PostMapping("/verificarSenha")
    public ResponseEntity salvarUsuario(@RequestBody Map<String, String> body){
        String senhaHash = passwordEncoder.encode(body.get("senha"));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Senha: " + senhaHash);
    }
}
