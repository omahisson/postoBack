package br.edu.ifsudestemg.demo.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BombaRequestDTO(
        @NotNull(message = "Código não pode ser nulo.") @NotBlank(message = "Código não pode estar em branco.") String codigo,
        @NotNull(message = "Número de série não pode ser nulo.") @NotBlank(message = "Número de série não pode estar em branco.") String numeroSerie,
        @NotNull(message = "ID do posto não pode ser nulo.") Long postoId
) {
}
