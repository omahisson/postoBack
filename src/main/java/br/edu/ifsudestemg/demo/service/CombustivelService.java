package br.edu.ifsudestemg.demo.service;

import br.edu.ifsudestemg.demo.exception.RegraNegocioException;
import br.edu.ifsudestemg.demo.model.entity.Combustivel;
import br.edu.ifsudestemg.demo.model.repository.CombustivelJpaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CombustivelService {
    private CombustivelJpaRepository repository;
    private DeleteDependencyCleaner deleteDependencyCleaner;

    public CombustivelService(CombustivelJpaRepository repository, DeleteDependencyCleaner deleteDependencyCleaner){
        this.repository = repository;
        this.deleteDependencyCleaner = deleteDependencyCleaner;
    }

    public List<Combustivel> getCombustivel(){
        return repository.findAll();
    }

    public List<Combustivel> getCombustivelByPosto(Long idPosto){
        return repository.findByPostoId(idPosto);
    }

    public Optional<Combustivel> getCombustivelById(Long id){
        return repository.findById(id);
    }

    @Transactional
    public Combustivel salvar(Combustivel combustivel){
        this.validar(combustivel);
        return repository.save(combustivel);
    }

    @Transactional
    public void excluir(Combustivel combustivel){
        Objects.requireNonNull(combustivel.getId());
        deleteDependencyCleaner.limparVinculosDoProduto(combustivel);
        repository.delete(combustivel);
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

    public void validar(Combustivel combustivel){
        validarString(combustivel.getNome(), "Nome invalido");
        validarNString(combustivel.getPreco(), "Preco invalido");
        validarString(combustivel.getUnidade(), "Unidade invalida");
        validarString(combustivel.getFornecedor(), "Fornecedor invalido");
        validarNString(combustivel.getEstoque(), "Estoque invalido");
        validarNString(combustivel.getPosto(), "Posto invalido");
        validarNString(combustivel.getAtivo(), "Ativo invalido");
    }
}
