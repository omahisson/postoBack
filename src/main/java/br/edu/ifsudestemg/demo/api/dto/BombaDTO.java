package br.edu.ifsudestemg.demo.api.dto;

import br.edu.ifsudestemg.demo.model.entity.Bomba;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@AllArgsConstructor
@Data
@NoArgsConstructor

public class BombaDTO {
    private Long id;
    private String codigo;
    private String numeroSerie;
    private Long idPosto;
    private Boolean ativo;

    public static BombaDTO create(Bomba body){
        ModelMapper modelMapper = new ModelMapper();
        BombaDTO dto = modelMapper.map(body, BombaDTO.class);
        if (body.getPosto() != null) {
            dto.setIdPosto(body.getPosto().getId());
        }
        return dto;
    }
}
