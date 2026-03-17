package br.edu.ifsudestemg.demo.model.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Venda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "data_hora")
    private LocalDateTime dataHora;
    @Column(name = "valor_bruto")
    private BigDecimal valorBruto;
    @Column(name = "valor_desconto")
    private BigDecimal valorDesconto;
    @Column(name = "valor_liquido")
    private BigDecimal valorLiquido;
    @Column(name = "forma_pagamento")
    private String formaPagamento;
    private String status;
}
