package br.edu.ifsudestemg.demo.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RegistroPreco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal valor;
    @Column(name = "data_inicio_vigencia")
    private Date dataInicioVigencia;
    @Column(name = "data_fim_vigencia")
    private Date dataFimVigencia;
    private boolean ativo;
}
