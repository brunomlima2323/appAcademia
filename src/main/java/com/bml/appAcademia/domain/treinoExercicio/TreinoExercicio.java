package com.bml.appAcademia.domain.treinoExercicio;

import com.bml.appAcademia.domain.exercicio.Exercicio;
import com.bml.appAcademia.domain.treino.Treino;
import com.bml.appAcademia.domain.treino.TreinoDTO;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "treino_exercicio")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class TreinoExercicio {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="treino_id")
    private Treino treino;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="exercicio_id")
    private Exercicio exercicio;

//    public TreinoExercicio(TreinoExercicioDTO dados) {
//        this.nomeTreino = dados.nomeTreino();
//    }
//
//    public void atualizarDados(@Valid TreinoDTO dados) {
//        if(dados.nomeTreino() != null){
//            this.nomeTreino = dados.nomeTreino();
//        }
//    }
}
