package br.edu.ifsudestemg.demo.api.dto;

import br.edu.ifsudestemg.demo.model.entity.Servico;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

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
    private Long idPosto;
    private Boolean ativo;

    public static ServicoDTO create(Servico body){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(body, ServicoDTO.class);
    }
}
