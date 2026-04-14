package br.edu.ifsudestemg.demo.service;

import br.edu.ifsudestemg.demo.model.entity.Produto;
import br.edu.ifsudestemg.demo.model.repository.PostoJpaRepository;
import br.edu.ifsudestemg.demo.model.repository.ProdutoJpaRepository;

import java.util.List;
import java.util.Optional;

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
}
