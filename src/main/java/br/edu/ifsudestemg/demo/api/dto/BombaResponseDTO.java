package br.edu.ifsudestemg.demo.api.dto;

public record BombaResponseDTO(
        Long id,
        String codigo,
        String numeroSerie,
        Long postoId,
        Boolean ativo
) {
}
