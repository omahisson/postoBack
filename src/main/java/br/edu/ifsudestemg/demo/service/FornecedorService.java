package br.edu.ifsudestemg.demo.service;

import br.edu.ifsudestemg.demo.model.entity.Fornecedor;
import br.edu.ifsudestemg.demo.model.repository.FornecedorJpaRepository;
import br.edu.ifsudestemg.demo.service.utils.EnderecoValidator;
import br.edu.ifsudestemg.demo.service.utils.PessoaJuridicaValidator;
import br.edu.ifsudestemg.demo.service.utils.PessoaValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FornecedorService {
    private final FornecedorJpaRepository repository;

    public List<Fornecedor> getFornecedores(){
        return repository.findAll();
    }

    public Optional<Fornecedor> getFornecedor(Long id){
        return repository.findById(id);
    }

    @Transactional
    public Fornecedor salvar(Fornecedor fornecedor){
        validar(fornecedor);
        fornecedor.setDataCadastro(LocalDate.now());
        fornecedor.setAtivo(true);
        return repository.save(fornecedor);
    }

    @Transactional
    public void excluir(Fornecedor fornecedor){
        Objects.requireNonNull(fornecedor.getId());
        repository.delete(fornecedor);
    }

    private void validar(Fornecedor fornecedor){
        EnderecoValidator.validar(fornecedor.getTelefone(), fornecedor.getEmail(), fornecedor.getLogradouro(), fornecedor.getNumero(), fornecedor.getBairro(), fornecedor.getCidade(), fornecedor.getEstado(), fornecedor.getCep());
        PessoaValidator.validar(fornecedor.getNome(), fornecedor.getPosto());
        PessoaJuridicaValidator.validar(fornecedor.getCnpj(), fornecedor.getRazaoSocial(), fornecedor.getNomeFantasia(), fornecedor.getInscricaoEstadual());
        if(fornecedor.getContatoResponsavel() == null || fornecedor.getContatoResponsavel().trim().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Contato Responsável não deve ser vazio.");
        }
        if(fornecedor.getPrazoEntregaDias() == null || fornecedor.getPrazoEntregaDias() < 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Prazo de entrega não deve ser vazio ou abaixo de zero.");
        }
        if(fornecedor.getCategoriaFornecimento() == null || fornecedor.getCategoriaFornecimento().trim().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Categoria do fornecimento não deve ser vazio.");
        }
    }
}
