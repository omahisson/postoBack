package br.edu.ifsudestemg.demo.service;

import br.edu.ifsudestemg.demo.model.entity.Compra;
import br.edu.ifsudestemg.demo.model.entity.Promocao;
import br.edu.ifsudestemg.demo.model.repository.CompraJpaRepository;

import java.util.List;
import java.util.Optional;

public class CompraService {
    private CompraJpaRepository repository;

    public CompraService(CompraJpaRepository repository){
        this.repository = repository;
    }

    public List<Compra> getCompra(){
        return repository.findAll();
    }

    public Optional<Compra> getCompraById(Long id){
        return repository.findById(id);
    }
}
