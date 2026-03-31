package br.edu.ifsudestemg.demo.api.dto;

import br.edu.ifsudestemg.demo.infrastructuries.enums.TipoDesconto;
import br.edu.ifsudestemg.demo.model.entity.Promocao;
import lombok.*;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PromocaoDTO {
    private Integer id;
    private String titulo;
    private String descricao;
    private TipoDesconto tipoDesconto;
    private BigDecimal valorDesconto;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private Boolean ativo;

    public static PromocaoDTO create(Promocao body){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(body, PromocaoDTO.class);
    }

}
