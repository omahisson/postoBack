package br.edu.ifsudestemg.demo.api.dto;

import br.edu.ifsudestemg.demo.model.entity.Fornecedor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FornecedorDTO {
    private String contatoResponsavel;
    private Integer prazoEntregaDias;
    private String categoriaFornecimento;

    private String cnpj;
    private String razaoSocial;
    private String nomeFantasia;
    private String inscricaoEstadual;

    private Long id;
    private String nome;
    private LocalDate dataCadastro;
    private Long idPosto;
    private Boolean ativo;
    private String telefone;
    private String email;
    private String logradouro;
    private String numero;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;

    public static FornecedorDTO create(Fornecedor body){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(body, FornecedorDTO.class);
    }
}
