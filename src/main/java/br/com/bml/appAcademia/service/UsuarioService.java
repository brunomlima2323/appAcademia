package br.com.bml.appAcademia.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.bml.appAcademia.dto.TreinoDTO;
import br.com.bml.appAcademia.dto.UsuarioDTO;
import br.com.bml.appAcademia.model.Usuario;
import br.com.bml.appAcademia.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository repositorio;

	public UsuarioDTO obterDadosUsuario() {
		Usuario usuario = repositorio.findById(1L).get();
		List<TreinoDTO> treino = usuario.getTreinos().stream()
				.map(t -> new TreinoDTO(t.getId(), t.getNomeTreino()))
				.collect(Collectors.toList());
		return new UsuarioDTO(usuario.getId(),usuario.getNome(),treino);
	}

}
