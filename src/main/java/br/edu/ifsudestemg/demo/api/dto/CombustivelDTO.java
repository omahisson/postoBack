package br.edu.ifsudestemg.demo.api.dto;

import br.edu.ifsudestemg.demo.model.entity.Combustivel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class CombustivelDTO {
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

    private String tipoCombustivel;
    private String octanagem;
    private String composicao;
    private String fornecedor;
    private String ultimoAbastecimento;

    public static CombustivelDTO create(Combustivel body){
        ModelMapper modelMapper = new ModelMapper();
        CombustivelDTO dto = modelMapper.map(body, CombustivelDTO.class);
        if (body.getPosto() != null) {
            dto.setIdPosto(body.getPosto().getId());
        }
        return dto;
    }
}
