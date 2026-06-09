package br.edu.ifsudestemg.demo.api.dto;

import br.edu.ifsudestemg.demo.infrastructuries.enums.TipoDesconto;
import br.edu.ifsudestemg.demo.model.entity.Promocao;
import lombok.*;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PromocaoDTO {
    private Long id;
    private String titulo;
    private String descricao;
    private TipoDesconto tipoDesconto;
    private BigDecimal valorDesconto;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private List<Long> idsProdutos;
    private List<Long> idsServicos;
    private Boolean ativo;

    public static PromocaoDTO create(Promocao body){
        ModelMapper modelMapper = new ModelMapper();
        PromocaoDTO dto = modelMapper.map(body, PromocaoDTO.class);
        if (body.getProdutos() != null) {
            dto.setIdsProdutos(body.getProdutos().stream().map(produto -> produto.getId()).toList());
        }
        if (body.getServicos() != null) {
            dto.setIdsServicos(body.getServicos().stream().map(servico -> servico.getId()).toList());
        }
        return dto;
    }

}
