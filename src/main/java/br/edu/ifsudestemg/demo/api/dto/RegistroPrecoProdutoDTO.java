package br.edu.ifsudestemg.demo.api.dto;

import br.edu.ifsudestemg.demo.model.entity.RegistroPrecoProduto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RegistroPrecoProdutoDTO {
    private Long id;
    private BigDecimal valor;
    private LocalDate dataInicioVigencia;
    private LocalDate dataFimVigencia;
    private Boolean ativo;
    private Long idProduto;

    public static RegistroPrecoProdutoDTO create(RegistroPrecoProduto body){
        ModelMapper modelMapper = new ModelMapper();
        RegistroPrecoProdutoDTO dto = modelMapper.map(body, RegistroPrecoProdutoDTO.class);
        if (body.getProduto() != null) {
            dto.setIdProduto(body.getProduto().getId());
        }
        return dto;
    }
}
