package br.edu.ifsudestemg.demo.service;

import br.edu.ifsudestemg.demo.model.entity.Promocao;
import br.edu.ifsudestemg.demo.model.repository.PromocaoJpaRepository;

import java.util.List;
import java.util.Optional;

public class PromocaoService {
    private PromocaoJpaRepository repository;

    public PromocaoService(PromocaoJpaRepository repository) {
        this.repository = repository;
    }

    public List<Promocao> getPromocao(){
        return repository.findAll();
    }

    public Optional<Promocao> getPromocaoById(Long id){
        return repository.findById(id);
    }
}
