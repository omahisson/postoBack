package br.edu.ifsudestemg.demo.api.dto;

import br.edu.ifsudestemg.demo.model.entity.Posto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TurnoDTO {
    private Long id;
    private String nome;
    private LocalTime horaInicio;
    private LocalTime horaFim;
    private long idPosto;
    private Boolean ativo;
}
