package br.edu.ifsudestemg.demo.api.dto;

import br.edu.ifsudestemg.demo.model.entity.Produto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ProdutoDTO {
    private Long id;
    private String nome;
    private BigDecimal preco;
    private String descricao;
    private String unidade;
    private Long idPosto;
    private Boolean ativo;
    private String codigoBarras;
    private String marca;
    private String categoria;
    private BigDecimal estoque;
    private LocalDate dataValidade;

    public static ProdutoDTO create(Produto body){
        ModelMapper modelMapper = new ModelMapper();
        ProdutoDTO dto = modelMapper.map(body, ProdutoDTO.class);
        if (body.getPosto() != null) {
            dto.setIdPosto(body.getPosto().getId());
        }
        return dto;
    }
}
