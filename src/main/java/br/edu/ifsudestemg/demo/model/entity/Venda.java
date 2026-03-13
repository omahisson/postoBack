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
    Long id;
    @Column(name = "data_hora")
    LocalDateTime dataHora;
    @Column(name = "valor_bruto")
    BigDecimal valorBruto;
    @Column(name = "valor_desconto")
    BigDecimal valorDesconto;
    @Column(name = "valor_liquido")
    BigDecimal valorLiquido;
    @Column(name = "forma_pagamento")
    String formaPagamento;
    String status;
}
