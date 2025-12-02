package com.bml.appAcademia.domain.treino;

import com.bml.appAcademia.domain.usuario.DadosAutenticacaoDTO;
import com.bml.appAcademia.domain.usuario.Usuario;
import jakarta.validation.constraints.NotBlank;

public record TreinoDTO(Long id, @NotBlank String nomeTreino) {

    public TreinoDTO(Treino treino){
        this(treino.getId(), treino.getNomeTreino());
    }
}
