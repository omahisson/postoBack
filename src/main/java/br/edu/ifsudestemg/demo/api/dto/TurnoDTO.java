package br.edu.ifsudestemg.demo.api.dto;

import br.edu.ifsudestemg.demo.model.entity.Turno;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TurnoDTO {
    private Long id;
    private String nome;
    private LocalTime horaInicio;
    private LocalTime horaFim;
    private Long idPosto;
    private Boolean ativo;

    public static TurnoDTO create(Turno body){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(body, TurnoDTO.class);
    }
}
