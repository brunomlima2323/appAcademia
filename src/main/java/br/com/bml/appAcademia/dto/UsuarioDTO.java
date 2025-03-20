package br.com.bml.appAcademia.dto;
import java.util.List;
import br.com.bml.appAcademia.dto.TreinoDTO;

public record UsuarioDTO(long id, String nome,List<TreinoDTO> treinoDTO) {
}
