package br.edu.ifsudestemg.demo.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class PessoaFisica extends Pessoa{
    private String cpf;
    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;
    private String rg;
}
