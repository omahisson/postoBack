package br.edu.ifsudestemg.demo.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RegistroPrecoProduto extends RegistroPreco{
    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;
}
