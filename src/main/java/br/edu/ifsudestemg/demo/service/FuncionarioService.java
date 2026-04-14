package br.edu.ifsudestemg.demo.service;

import br.edu.ifsudestemg.demo.model.entity.Funcionario;
import br.edu.ifsudestemg.demo.model.entity.Posto;
import br.edu.ifsudestemg.demo.model.repository.FuncionarioJpaRepository;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FuncionarioService {
    private final FuncionarioJpaRepository repository;

    public List<Funcionario> getFuncionarios(){
        return repository.findAll();
    }

    public Optional<Funcionario> getFuncionario(Long id){
        return repository.findById(id);
    }

    @Transactional
    public Funcionario salvar(Funcionario funcionario){
        validar(funcionario);
        return repository.save(funcionario);
    }

    @Transactional
    public void excluir(Funcionario funcionario){
        Objects.requireNonNull(funcionario.getId());
        repository.delete(funcionario);
    }

    private void validar(Funcionario funcionario){

        private String nome;

        private LocalDate dataCadastro;
        @ManyToOne
        @JoinColumn(name = "posto_id")
        private Posto posto;

        private Boolean ativo;
        private String cpf;
        private LocalDate dataNascimento;
        private String rg;
        private String maticula;
        private BigDecimal salario;
        private LocalDate dataAdmissao;
        private String senha;
        private String setor;

        private String telefone;
        private String email;
        private String logradouro;
        private String numero;
        private String bairro;
        private String cidade;
        private String estado;
        private String cep;

        private BigDecimal bonusMeta;
    }
}
