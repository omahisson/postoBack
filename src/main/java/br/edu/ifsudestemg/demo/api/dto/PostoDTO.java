package br.edu.ifsudestemg.demo.api.dto;

import br.edu.ifsudestemg.demo.model.entity.Posto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class PostoDTO {
    private String telefone;
    private String email;
    private String logradouro;
    private String numero;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;

    private Long id;
    private Long nome;
    private LocalDate dataCadastro;
    private Long idPosto;
    private Boolean ativo;

    private String cnpj;
    private String razaoSocial;
    private String nomeFantasia;
    private String inscricaoEstadual;

    private LocalDate dataAbertura;

    public static PostoDTO create(Posto body){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(body, PostoDTO.class);
    }
}
