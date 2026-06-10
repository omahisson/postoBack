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
public class HistoricoCombustivel {
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
    private BigDecimal precoAnterior;
    private BigDecimal novoPreco;
    private LocalDate dataVigencia;
    private String responsavel;
    private String motivo;
    private LocalDate dataAlteracao;
}
