package br.edu.ifsudestemg.demo.service;

import br.edu.ifsudestemg.demo.model.entity.Produto;
import br.edu.ifsudestemg.demo.model.repository.ProdutoJpaRepository;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static br.edu.ifsudestemg.demo.service.utils.EnderecoValidator.validar;

public class ProdutoService {
    private ProdutoJpaRepository repository;

    public ProdutoService(ProdutoJpaRepository repository){
        this.repository = repository;
    }

    public List<Produto> getPosto(){
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
}
