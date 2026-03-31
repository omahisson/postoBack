package br.edu.ifsudestemg.demo.api.dto;

import br.edu.ifsudestemg.demo.model.entity.Funcionario;
import lombok.*;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FuncionarioDTO {
    private String maticula;
    private BigDecimal salario;
    private LocalDate dataAdmissao;
    private String senha;
    private String setor;
    private BigDecimal bonusMeta;
    private String cpf;
    private LocalDate dataNascimento;
    private String rg;
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

    public static FuncionarioDTO create(Funcionario body){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(body, FuncionarioDTO.class);
    }
}
