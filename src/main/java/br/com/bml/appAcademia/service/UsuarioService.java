package br.com.bml.appAcademia.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.bml.appAcademia.dto.TreinoDTO;
import br.com.bml.appAcademia.dto.UsuarioDTO;
import br.com.bml.appAcademia.model.Treino;
import br.com.bml.appAcademia.model.Usuario;
import br.com.bml.appAcademia.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository repositorio;

	public UsuarioDTO obterDadosUsuario(long usuario_id) {
		Usuario usuario = repositorio.findById(usuario_id).get();
		List<TreinoDTO> treino = usuario.getTreinos().stream()
				.map(t -> new TreinoDTO(t.getId(), t.getNomeTreino()))
				.collect(Collectors.toList());
		return new UsuarioDTO(usuario.getId(),usuario.getNome(),treino);
	}
	
	public TreinoDTO obterDadosTrinoUsuario(long usuario_id, long treino_id) {
		Treino treino = repositorio.getUsuarioETreinoPorId(usuario_id, treino_id);
		
		return new TreinoDTO(treino.getId(),treino.getNomeTreino());
	}

}
