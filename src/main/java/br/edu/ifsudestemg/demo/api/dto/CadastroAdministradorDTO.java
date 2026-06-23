package br.edu.ifsudestemg.demo.api.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CadastroAdministradorDTO {
    private String nome;
    private String matricula;
    private String senha;
    private String senhaRepeticao;
    private String cpf;
    private LocalDate dataNascimento;
    private String rg;
    private String telefone;
    private String email;
    private String logradouro;
    private String numero;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;
    private BigDecimal salario;
}
