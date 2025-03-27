package br.com.bml.appAcademia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import br.com.bml.appAcademia.dto.TreinoDTO;
import br.com.bml.appAcademia.dto.UsuarioDTO;
import br.com.bml.appAcademia.service.UsuarioService;

@RestController
public class UsuarioController {
	
	@Autowired
	private UsuarioService servico;
	
	@GetMapping("/home/{usuario_id}")
	public UsuarioDTO obterDadosUsuario(@PathVariable long usuario_id) {
		return servico.obterDadosUsuario(usuario_id);
	}
	
	@GetMapping("/treino/{usuario_id}/{treino_id}")
	public TreinoDTO obterDadosTrinoUsuario(@PathVariable Long usuario_id, @PathVariable Long treino_id) {
		return servico.obterDadosTrinoUsuario(usuario_id, treino_id);
	}

}
