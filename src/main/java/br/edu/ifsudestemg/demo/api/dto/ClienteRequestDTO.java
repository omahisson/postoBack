package br.edu.ifsudestemg.demo.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ClienteRequestDTO(
        @NotNull BigDecimal limiteCredito,
        @NotNull Long pontosFidelidade,
        @NotNull @NotBlank String observacoes,
        @NotNull @NotBlank String cpf,
        @NotNull LocalDate dataNascimento,
        @NotNull @NotBlank String rg,
        @NotNull Long id,
        @NotNull @NotBlank String nome,
        @NotNull LocalDate dataCadastro,
        @NotNull Long idPposto,
        @NotNull Boolean ativo,
        @NotNull @NotBlank String telefone,
        @NotNull @NotBlank String email,
        @NotNull @NotBlank String logradouro,
        @NotNull @NotBlank String numero,
        @NotNull @NotBlank String bairro,
        @NotNull @NotBlank String cidade,
        @NotNull @NotBlank String estado,
        @NotNull @NotBlank String cep
) {
}
