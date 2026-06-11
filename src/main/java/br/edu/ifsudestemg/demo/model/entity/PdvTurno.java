package br.edu.ifsudestemg.demo.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PdvTurno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "posto_id")
    private Posto posto;
    @ManyToOne
    @JoinColumn(name = "operador_id")
    private Funcionario operador;
    private String operadorNome;
    private String turno;
    private LocalDateTime horaAbertura;
    private LocalDateTime horaFechamento;
    private BigDecimal valorInicialCaixa;
    private BigDecimal valorFinalCaixa;
    private BigDecimal valorEsperado;
    private BigDecimal diferenca;
    private BigDecimal totalVendas;
    private BigDecimal totalCartao;
    private BigDecimal totalDinheiro;
    private Integer totalTransacoes;
    private String status;
}
