package br.edu.ifsudestemg.demo.api.dto;

import br.edu.ifsudestemg.demo.model.entity.RegistroPrecoServico;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistroPrecoServicoDTO {
    private Long id;
    private BigDecimal valor;
    private LocalDate dataInicioVigencia;
    private LocalDate dataFimVigencia;
    private Boolean ativo;
    private Long idServico;

    public static RegistroPrecoServicoDTO create(RegistroPrecoServico body){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(body, RegistroPrecoServicoDTO.class);
    }
}
