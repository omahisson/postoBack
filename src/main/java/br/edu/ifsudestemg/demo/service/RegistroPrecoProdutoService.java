package br.edu.ifsudestemg.demo.service;

import br.edu.ifsudestemg.demo.model.entity.RegistroPrecoProduto;
import br.edu.ifsudestemg.demo.model.repository.RegistroPrecoProdutoJpaRepository;

import java.util.List;
import java.util.Optional;

public class RegistroPrecoProdutoService {
    private RegistroPrecoProdutoJpaRepository repository;

    public RegistroPrecoProdutoService(RegistroPrecoProdutoJpaRepository repository){
        this.repository = repository;
    }

    public List<RegistroPrecoProduto> getRegistroPrecoProduto(){
        return repository.findAll();
    }

    public Optional<RegistroPrecoProduto> getRegistroPrecoProdutoById(Long id){
        return repository.findById(id);
    }
}
