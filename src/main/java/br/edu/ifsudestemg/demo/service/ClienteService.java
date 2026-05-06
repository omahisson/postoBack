package br.edu.ifsudestemg.demo.service;

import br.edu.ifsudestemg.demo.model.entity.Cliente;
import br.edu.ifsudestemg.demo.model.repository.ClienteJpaRepository;
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
public class ClienteService {

    private final ClienteJpaRepository repository;

    public List<Cliente> getClientes(){
        return repository.findAll();
    }

    public Optional<Cliente> getClientes(Long id){
        return repository.findById(id);
    }

    @Transactional
    public Cliente salvar(Cliente cliente){
        validar(cliente);
        cliente.setDataCadastro(LocalDate.now());
        cliente.setAtivo(true);
        return repository.save(cliente);
    }

    @Transactional
    public void excluir(Cliente cliente){
        Objects.requireNonNull(cliente.getId());
        repository.delete(cliente);
    }

    private void validar(Cliente cliente){
        EnderecoValidator.validar(cliente.getTelefone(), cliente.getEmail(), cliente.getLogradouro(), cliente.getNumero(), cliente.getBairro(), cliente.getCidade(), cliente.getEstado(), cliente.getCep());
        PessoaValidator.validar(cliente.getNome(), cliente.getPosto());
        PessoaFisicaValidator.validar(cliente.getCpf(), cliente.getDataNascimento(), cliente.getRg());

        if (cliente.getLimiteCredito() == null || cliente.getLimiteCredito().compareTo(new BigDecimal(0))< 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Limite de crédito deve ser um valor positivo ou igual a zero.");
        }
        if(cliente.getPontosFidelidade() == null || cliente.getPontosFidelidade() < 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pontos de fidelidade deve ser um valor positivo ou igual a zero.");
        }
    }
}
