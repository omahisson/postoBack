package br.edu.ifsudestemg.demo.service;

import br.edu.ifsudestemg.demo.model.entity.Combustivel;
import br.edu.ifsudestemg.demo.model.repository.CombustivelJpaRepository;
import br.edu.ifsudestemg.demo.model.repository.PostoJpaRepository;

import java.util.List;
import java.util.Optional;

public class CombustivelService {
    private CombustivelJpaRepository repository;

    public CombustivelService(CombustivelJpaRepository repository){
        this.repository = repository;
    }

    public List<Combustivel> getCombustivel(){
        return repository.findAll();
    }

    public Optional<Combustivel> getCombustivelById(Long id){
        return repository.findById(id);
    }
}
