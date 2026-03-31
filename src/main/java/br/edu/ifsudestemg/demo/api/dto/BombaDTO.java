package br.edu.ifsudestemg.demo.api.dto;

import br.edu.ifsudestemg.demo.model.entity.Bomba;
import br.edu.ifsudestemg.demo.model.entity.Posto;
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
        return modelMapper.map(body, BombaDTO.class);
    }
}
