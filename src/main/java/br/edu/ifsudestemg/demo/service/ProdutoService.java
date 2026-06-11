package br.edu.ifsudestemg.demo.service;

import br.edu.ifsudestemg.demo.exception.RegraNegocioException;
import br.edu.ifsudestemg.demo.model.entity.Produto;
import br.edu.ifsudestemg.demo.model.repository.ProdutoJpaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProdutoService {
    private ProdutoJpaRepository repository;
    private DeleteDependencyCleaner deleteDependencyCleaner;

    public ProdutoService(ProdutoJpaRepository repository, DeleteDependencyCleaner deleteDependencyCleaner){
        this.repository = repository;
        this.deleteDependencyCleaner = deleteDependencyCleaner;
    }

    public List<Produto> getProduto(){
        return repository.findProdutos();
    }

    public List<Produto> getProdutoByPosto(Long idPosto){
        return repository.findProdutosByPostoId(idPosto);
    }

    public Optional<Produto> getProdutoById(Long id){
        return repository.findById(id);
    }

    @Transactional
    public Produto salvar(Produto produto){
        this.validar(produto);
        return repository.save(produto);
    }

    @Transactional
    public void excluir(Produto produto){
        Objects.requireNonNull(produto.getId());
        deleteDependencyCleaner.limparVinculosDoProduto(produto);
        repository.delete(produto);
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

    public void validar(Produto produto){
        validarString(produto.getCodigoBarras(), "Codigo barras invalido");
        validarString(produto.getNome(), "Nome invalido");
        validarNString(produto.getPreco(), "Preco invalido");
        validarNString(produto.getEstoque(), "Estoque invalido");
        validarNString(produto.getPosto(), "Posto invalido");
        validarNString(produto.getAtivo(), "Ativo invalido");
    }
}
