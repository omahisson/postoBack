package br.edu.ifsudestemg.demo.service;

import br.edu.ifsudestemg.demo.model.entity.RegistroPrecoServico;
import br.edu.ifsudestemg.demo.model.repository.RegistroPrecoServicoJpaRepository;

import java.util.List;
import java.util.Optional;

public class RegistroPrecoServicoService {
    private RegistroPrecoServicoJpaRepository repository;

    public RegistroPrecoServicoService(RegistroPrecoServicoJpaRepository repository) {
        this.repository = repository;
    }

    public List<RegistroPrecoServico> getRegistroPrecoServico(){
        return repository.findAll();
    }

    public Optional<RegistroPrecoServico> getRegistroPrecoServicoById(Long id){
        return repository.findById(id);
    }
}
