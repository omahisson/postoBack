package br.edu.ifsudestemg.demo.api.dto;

import br.edu.ifsudestemg.demo.model.entity.Posto;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {
    private BigDecimal limiteCredito;
    private Long pontosFidelidade;
    private String observacoes;
    private String cpf;
    private LocalDate dataNascimento;
    private String rg;
    private Long id;
    private String nome;
    private LocalDate dataCadastro;
    private Posto posto;
    private Boolean ativo;
    private String telefone;
    private String email;
    private String logradouro;
    private String numero;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;
}
