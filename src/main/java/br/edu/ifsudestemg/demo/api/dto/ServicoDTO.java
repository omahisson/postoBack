package br.edu.ifsudestemg.demo.api.dto;

import br.edu.ifsudestemg.demo.model.entity.Posto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServicoDTO {
    private String duracaoEstimadaMinutos;
    private Boolean requerAgendamento;
    private String descricaoTecnica;

    private Long id;
    private String nome;
    private BigDecimal preco;
    private String descricao;
    private String unidade;
    private Posto posto;
    private Boolean ativo;
}
