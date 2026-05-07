package br.edu.ifsudestemg.demo.service;

import br.edu.ifsudestemg.demo.exception.RegraNegocioException;
import br.edu.ifsudestemg.demo.model.entity.Posto;
import br.edu.ifsudestemg.demo.model.repository.PostoJpaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static br.edu.ifsudestemg.demo.service.utils.EnderecoValidator.validar;
@Service

public class PostoService {
    private PostoJpaRepository repository;

    public PostoService(PostoJpaRepository repository){
        this.repository = repository;
    }

    public List<Posto> getPosto(){
        return repository.findAll();
    }

    public Optional<Posto> getPostoById(Long id){
        return repository.findById(id);
    }

    @Transactional
    public Posto salvar(Posto posto){
        validar(posto);
        return repository.save(posto);
    }

    @Transactional
    public void excluir(Posto posto){
        Objects.requireNonNull(posto.getId());;
        repository.delete(posto);
    }

    public void validarString(String valor, String mensagem){
        if(valor == null || valor.isBlank()){
            throw new RegraNegocioException(mensagem);
        }
    }

    public void validarNString(Object valor, String mensagem){
        if(valor == null){
            throw new RegraNegocioException(mensagem);
        }
    }

    public void validar(Posto posto){
        validarNString(posto.getDataAbertura(), "Data invalida");
        validarString(posto.getCnpj(), "Cnpj invalido");
        validarString(posto.getRazaoSocial(), "Razao social invalida");
        validarString(posto.getNomeFantasia(), "Nome fantasia invalido");
        validarString(posto.getInscricaoEstadual(), "Inscricao estadual invalido");
        validarString(posto.getNome(), "Nome invalido");
        validarNString(posto.getDataCadastro(), "Data cadastro invalido");
        validarNString(posto.getPosto(), "Posto invalido");
        validarNString(posto.getAtivo(), "Valor não valido");
        validarString(posto.getTelefone(), "Telefone invalido");
        validarString(posto.getEmail(), "Email invalido");
        validarString(posto.getLogradouro(), "Logradouro invalido");
        validarString(posto.getNumero(), "Numero invalido");
        validarString(posto.getBairro(), "Bairro invalido");
        validarString(posto.getCidade(), "Cidade invalido");
        validarString(posto.getEstado(), "Estado invalido");
        validarString(posto.getCep(), "Cep invalido");
    }

}
