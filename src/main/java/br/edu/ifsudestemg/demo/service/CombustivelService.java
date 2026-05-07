package br.edu.ifsudestemg.demo.service;

import br.edu.ifsudestemg.demo.model.entity.Combustivel;
import br.edu.ifsudestemg.demo.model.entity.Posto;
import br.edu.ifsudestemg.demo.model.repository.CombustivelJpaRepository;
import br.edu.ifsudestemg.demo.model.repository.PostoJpaRepository;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static br.edu.ifsudestemg.demo.service.utils.EnderecoValidator.validar;

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

    @Transactional
    public Combustivel salvar(Combustivel combustivel){
        validar(combustivel);
        return repository.save(combustivel);
    }

    @Transactional
    public void excluir(Combustivel combustivel){
        Objects.requireNonNull(combustivel.getId());;
        repository.delete(combustivel);
    }
}
