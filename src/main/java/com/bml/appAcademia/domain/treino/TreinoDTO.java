package com.bml.appAcademia.domain.treino;

import com.bml.appAcademia.domain.usuario.DadosAutenticacaoDTO;
import com.bml.appAcademia.domain.usuario.Usuario;
import jakarta.validation.constraints.NotBlank;

public record TreinoDTO(Long id, @NotBlank String nomeTreino, Integer ordem, boolean atual) {

    public TreinoDTO(Treino treino, boolean atual){
        this(treino.getId(), treino.getNomeTreino(), treino.getOrdem(), atual);
    }
}
