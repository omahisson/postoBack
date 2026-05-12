package br.edu.ifsudestemg.demo.service;

import br.edu.ifsudestemg.demo.exception.RegraNegocioException;
import br.edu.ifsudestemg.demo.model.entity.Produto;
import br.edu.ifsudestemg.demo.model.repository.ProdutoJpaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static br.edu.ifsudestemg.demo.service.utils.EnderecoValidator.validar;

@Service
public class ProdutoService {
    private ProdutoJpaRepository repository;

    public ProdutoService(ProdutoJpaRepository repository){
        this.repository = repository;
    }

    public List<Produto> getProduto(){
        return repository.findAll();
    }

    public Optional<Produto> getProdutoById(Long id){
        return repository.findById(id);
    }

    @Transactional
    public Produto salvar(Produto produto){
        validar(produto);
        return repository.save(produto);
    }

    @Transactional
    public void excluir(Produto produto){
        Objects.requireNonNull(produto.getId());;
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
        validarString(produto.getMarca(), "Marca invalido");
        validarString(produto.getCategoria(), "Categoria invalido");
        validarString(produto.getNome(), "Nome invalido");
        validarNString(produto.getPreco(), "Preço invalido"); //validar dps negativo
        validarString(produto.getDescricao(), "Descricao invalido");
        validarString(produto.getUnidade(), "Unidade invalido");
        validarNString(produto.getPosto(), "Posto invalido");
        validarNString(produto.getAtivo(), "Ativo invalido");
    }
}
