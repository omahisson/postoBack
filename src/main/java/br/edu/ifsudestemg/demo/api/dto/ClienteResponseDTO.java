package br.edu.ifsudestemg.demo.api.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ClienteResponseDTO(
        BigDecimal limiteCredito,
        Long pontosFidelidade,
        String observacoes,
        String cpf,
        LocalDate dataNascimento,
        String rg,
        Long id,
        String nome,
        LocalDate dataCadastro,
        Long idPposto,
        Boolean ativo,
        String telefone,
        String email,
        String logradouro,
        String numero,
        String bairro,
        String cidade,
        String estado,
        String cep
) {
}
