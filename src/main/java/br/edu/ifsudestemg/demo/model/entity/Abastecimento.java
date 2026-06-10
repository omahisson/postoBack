package br.edu.ifsudestemg.demo.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Abastecimento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "posto_id")
    private Posto posto;
    @ManyToOne
    @JoinColumn(name = "combustivel_id")
    private Combustivel combustivel;
    private String tipoCombustivel;
    private String fornecedor;
    private BigDecimal quantidade;
    private String unidade;
    private String numeroNota;
    private LocalDate dataEntrega;
    private LocalDate dataValidade;
    private BigDecimal precoUnitario;
    private BigDecimal valorTotal;
}
