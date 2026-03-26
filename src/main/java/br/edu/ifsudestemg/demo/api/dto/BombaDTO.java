package br.edu.ifsudestemg.demo.api.dto;

import br.edu.ifsudestemg.demo.model.entity.Posto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor

public class BombaDTO {
    private Long id;
    private String codigo;
    private String numeroSerie;
    private Posto posto;
    private Boolean ativo;
}
