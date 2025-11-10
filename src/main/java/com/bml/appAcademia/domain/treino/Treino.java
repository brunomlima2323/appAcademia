package com.bml.appAcademia.domain.treino;

import com.bml.appAcademia.domain.usuario.Usuario;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "treinos")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Treino {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomeTreino;
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    public Treino(TreinoDTO dados) {
        this.nomeTreino = dados.nomeTreino();
    }

    public void atualizarDados(@Valid TreinoDTO dados) {
        if(dados.nomeTreino() != null){
            this.nomeTreino = dados.nomeTreino();
        }
    }

}
