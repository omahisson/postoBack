package br.edu.ifsudestemg.demo.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente extends PessoaFisica{
    @Column(name = "limite_credito")
    private BigDecimal limiteCredito;
    @Column(name = "pontos_fidelidade")
    private Long pontosFidelidade;
    private String observacoes;
}
