package br.edu.ifsudestemg.demo.model.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PdvVendaItem {
    private String tipo;
    private Long itemId;
    private String titulo;
    private BigDecimal quantidade;
    private BigDecimal precoUnitario;
    private BigDecimal valorTotal;
    private String unidade;
}
