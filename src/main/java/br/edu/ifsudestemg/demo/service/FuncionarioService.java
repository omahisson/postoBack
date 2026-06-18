package br.edu.ifsudestemg.demo.service;

import br.edu.ifsudestemg.demo.infrastructuries.enums.Cargo;
import br.edu.ifsudestemg.demo.model.entity.Funcionario;
import br.edu.ifsudestemg.demo.model.repository.FuncionarioJpaRepository;
import br.edu.ifsudestemg.demo.service.utils.EnderecoValidator;
import br.edu.ifsudestemg.demo.service.utils.PessoaFisicaValidator;
import br.edu.ifsudestemg.demo.service.utils.PessoaValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
        funcionario.setDataCadastro(LocalDate.now());
        funcionario.setAtivo(true);
        return repository.save(funcionario);
    }

    @Transactional
    public void excluir(Funcionario funcionario){
        Objects.requireNonNull(funcionario.getId());
        repository.delete(funcionario);
    }

    private void validar(Funcionario funcionario){

        PessoaValidator.validar(funcionario.getNome(), funcionario.getPosto());

        PessoaFisicaValidator.validar(funcionario.getCpf(), funcionario.getDataNascimento(), funcionario.getRg());

        EnderecoValidator.validar(funcionario.getTelefone(), funcionario.getEmail(), funcionario.getLogradouro(), funcionario.getNumero(), funcionario.getBairro(), funcionario.getCidade(), funcionario.getEstado(), funcionario.getCep());

        if(funcionario.getMaticula() == null || funcionario.getMaticula().trim().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Matrícula não deve ser vazio.");
        }
        if(funcionario.getDataAdmissao() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Data de admissão não deve ser vazio.");
        }
        if(funcionario.getSenha() == null || funcionario.getSenha().trim().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Senha não deve ser vazio.");
        }
        if(funcionario.getSenha().length() < 8){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Senha deve possuir ao menos 8 caracteres.");
        }
        if(funcionario.getSetor() == null || funcionario.getSetor().trim().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Setor não deve ser vazio.");
        }
        if (funcionario.getSalario() == null || funcionario.getSalario().compareTo(new BigDecimal(0))<= 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Salário ser um valor válido maior que zero.");
        }
        if(funcionario.getCargo() == Cargo.GERENTE){
            if (funcionario.getBonusMeta() == null || funcionario.getBonusMeta().compareTo(new BigDecimal(0))<= 0){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bonus de meta ser um valor válido maior que zero.");
            }
        }
    }
}
