package br.edu.ifsudestemg.demo.model.entity;

import br.edu.ifsudestemg.demo.infrastructuries.enums.Cargo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@DiscriminatorValue("GERENTE")
@Getter
@Setter
public class Gerente extends Funcionario{
    @ElementCollection
    @CollectionTable(name = "gerente_postos_vinculados", joinColumns = @JoinColumn(name = "gerente_id"))
    @Column(name = "posto_id")
    private Set<Long> postosVinculados = new LinkedHashSet<>();

    @Override
    public Cargo getCargo(){
        return Cargo.GERENTE;
    }
}
