package br.edu.ifsudestemg.demo.model.entity;

import br.edu.ifsudestemg.demo.infrastructuries.enums.TipoDesconto;
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
public class Promocao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String titulo;
    private String descricao;
    @Column(name = "tipo_desconto")
    @Enumerated(EnumType.STRING)
    private TipoDesconto tipoDesconto;
    @Column(name = "valor_desconto")
    private BigDecimal valorDesconto;
    @Column(name = "data_inicio")
    private LocalDate dataInicio;
    @Column(name = "data_fim")
    private LocalDate dataFim;
    private Boolean ativo;
}
