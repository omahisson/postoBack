package br.edu.ifsudestemg.demo.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RegistroPrecoServico extends RegistroPreco{
    @ManyToOne
    @JoinColumn(name = "servico_id")
    private Servico servico;
}
