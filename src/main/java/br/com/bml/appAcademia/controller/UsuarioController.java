package br.com.bml.appAcademia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.bml.appAcademia.dto.UsuarioDTO;
import br.com.bml.appAcademia.service.UsuarioService;

@RestController
public class UsuarioController {
	
	@Autowired
	private UsuarioService servico;
	
	@GetMapping("/home/{usuario_id}")
	public UsuarioDTO obterDadosUsuario() {
		return servico.obterDadosUsuario();
	}

}
