package br.edu.ifsudestemg.demo.model.entity;

import br.edu.ifsudestemg.demo.infrastructuries.enums.FormaPagamento;
import br.edu.ifsudestemg.demo.infrastructuries.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Compra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "data_compra")
    private LocalDate dataCompra;
    @Column(name = "valor_total")
    private BigDecimal valorTotal;
    @Column(name = "nota_fiscal")
    private String numeroNotaFiscal;
    @Column(name = "forma_pagamento")
    private FormaPagamento formaPagamento;
    private Status status;
}
