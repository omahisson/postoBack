package br.edu.ifsudestemg.demo.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PdvFechamentoDTO {
    private BigDecimal valorFinalCaixa;
    private BigDecimal valorEsperado;
    private BigDecimal diferenca;
}
