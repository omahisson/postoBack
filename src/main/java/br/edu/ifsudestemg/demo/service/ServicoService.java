package br.edu.ifsudestemg.demo.service;

import br.edu.ifsudestemg.demo.model.entity.Servico;
import br.edu.ifsudestemg.demo.model.repository.ServicoJpaRepository;

import java.util.List;
import java.util.Optional;

public class ServicoService {
    private ServicoJpaRepository repository;

    public ServicoService(ServicoJpaRepository repository) {
        this.repository = repository;
    }

    public List<Servico> getServico(){
        return repository.findAll();
    }

    public Optional<Servico> getServicoById(Long id){
        return repository.findById(id);
    }
}
