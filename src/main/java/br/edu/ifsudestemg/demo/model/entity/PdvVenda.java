package br.edu.ifsudestemg.demo.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PdvVenda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "posto_id")
    private Posto posto;
    @ManyToOne
    @JoinColumn(name = "turno_id")
    private PdvTurno turno;
    private LocalDateTime data;
    private BigDecimal total;
    private String formaPagamento;
    private Boolean cancelada;
    private LocalDateTime canceladaEm;
    private String motivoCancelamento;
    @ElementCollection
    @CollectionTable(name = "pdv_venda_item", joinColumns = @JoinColumn(name = "venda_id"))
    private List<PdvVendaItem> itens = new ArrayList<>();
}
