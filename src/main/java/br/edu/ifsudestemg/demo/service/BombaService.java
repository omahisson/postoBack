package br.edu.ifsudestemg.demo.service;

import br.edu.ifsudestemg.demo.model.entity.Bomba;
import br.edu.ifsudestemg.demo.model.repository.BombaJpaRepository;

import java.util.List;
import java.util.Optional;

public class BombaService {
    private BombaJpaRepository repository;

    public BombaService(BombaJpaRepository repository){
        this.repository = repository;
    }
    public List<Bomba> getBomba(){
        return repository.findAll();
    }
    public Optional<Bomba> getBombaById(Long id){
        return repository.findById(id);
    }
}
