package br.edu.ifsudestemg.demo.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Bomba {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String codigo;
    @Column(name = "numero_serie")
    private String numeroSerie;
    @ManyToOne
    @JoinColumn(name = "posto_id")
    private Posto posto;

    private Boolean ativo;
}
