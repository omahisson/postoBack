package br.edu.ifsudestemg.demo.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Pessoa extends Endereco{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    @Column(name = "data_cadastro")
    private LocalDate dataCadastro;
    @ManyToOne
    @JoinColumn(name = "posto_id")
    private Posto posto;
    private Boolean ativo;
}
