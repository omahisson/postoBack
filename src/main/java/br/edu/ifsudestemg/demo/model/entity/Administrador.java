package br.edu.ifsudestemg.demo.model.entity;

import br.edu.ifsudestemg.demo.infrastructuries.enums.Cargo;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("ADMINISTRADOR")
public class Administrador extends Funcionario{
    @Override
    public Cargo getCargo(){
        return Cargo.ADMINISTRADOR;
    }
}
