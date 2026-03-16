package br.edu.ifsudestemg.demo.model.entity;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Posto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeFantasia;
    @Column(unique = true)
    private String cnpj;
    private String telefone;
    private String email;

    private String logradouro;
    private int numero;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;

    @Column(name = "data_abertura")
    private LocalDate dataAbertura;

    private Boolean ativo;

    @OneToMany(mappedBy = "posto")
    private List<Bomba> bombas;

    @OneToMany(mappedBy = "posto")
    private List<Servico> servicos;

    @OneToMany(mappedBy = "posto")
    private List<RegistroPreco> registroPrecos;

    @OneToMany(mappedBy = "posto")
    private List<Promocao> promocoes;

    @OneToMany(mappedBy = "posto")
    private List<Compra> compras;

    @OneToMany(mappedBy = "posto")
    private List<Venda> vendas;
}
