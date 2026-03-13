package br.edu.ifsudestemg.demo.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Fornecedor extends PessoaJuridica{
    @Column(name = "contato_responsavel")
    private String contatoResponsavel;
    @Column(name = "prazo_entrega_dias")
    private Integer prazoEntregaDias;
    @Column(name = "categoria_fornecimento")
    private String categoriaFornecimento;
}
