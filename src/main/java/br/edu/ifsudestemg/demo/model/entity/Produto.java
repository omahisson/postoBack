package br.edu.ifsudestemg.demo.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Produto extends Vendivel{

    @Column(name = "codigo_barras")
    private String codigoBarras;
    private String marca;
    private String categoria;
}
