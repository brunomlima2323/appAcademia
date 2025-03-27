package br.com.bml.appAcademia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.bml.appAcademia.model.Treino;
import br.com.bml.appAcademia.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	
	 @Query("SELECT t FROM Usuario u JOIN u.treinos t WHERE u.id = :usuario_id AND t.id = :treino_id")
	 Treino getUsuarioETreinoPorId(@Param("usuario_id") Long usuario_id, @Param("treino_id") Long treino_id);
}
