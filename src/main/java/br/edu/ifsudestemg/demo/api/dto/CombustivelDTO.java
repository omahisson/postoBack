package br.edu.ifsudestemg.demo.api.dto;

import br.edu.ifsudestemg.demo.model.entity.Posto;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class CombustivelDTO {
    private Long id;
    private String nome;
    private BigDecimal preco;
    private String descricao;
    private String unidade;
    private long idPposto;
    private Boolean ativo;

    private String codigoBarras;
    private String marca;
    private String categoria;

    private String tipoCombustivel;
    private String octanagem;
    private String composicao;
}
