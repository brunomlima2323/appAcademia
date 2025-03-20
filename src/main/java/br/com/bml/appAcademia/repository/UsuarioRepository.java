package br.com.bml.appAcademia.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.bml.appAcademia.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

}
