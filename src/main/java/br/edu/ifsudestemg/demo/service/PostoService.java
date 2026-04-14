package br.edu.ifsudestemg.demo.service;

import br.edu.ifsudestemg.demo.model.entity.Posto;
import br.edu.ifsudestemg.demo.model.repository.PostoJpaRepository;

import java.util.List;
import java.util.Optional;

public class PostoService {
    private PostoJpaRepository repository;

    public PostoService(PostoJpaRepository repository){
        this.repository = repository;
    }

    public List<Posto> getPosto(){
        return repository.findAll();
    }

    public Optional<Posto> getPostoById(Long id){
        return repository.findById(id);
    }
}
