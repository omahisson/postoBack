package br.edu.ifsudestemg.demo.api.dto;

import br.edu.ifsudestemg.demo.model.entity.Posto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendaDTO {
    private Long id;
    private LocalDateTime dataHora;
    private BigDecimal valorBruto;
    private BigDecimal valorDesconto;
    private BigDecimal valorLiquido;
    private String formaPagamento;
    private Posto posto;
    private String status;
}
