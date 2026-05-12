package br.edu.ifsudestemg.demo.api.dto;

import java.time.LocalDateTime;

public record ErroPadronizadoDTO(
        String status,
        String mensagem,
        LocalDateTime timestamp
) {
}
