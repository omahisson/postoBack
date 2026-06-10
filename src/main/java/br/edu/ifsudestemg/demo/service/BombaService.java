package br.edu.ifsudestemg.demo.service;

import br.edu.ifsudestemg.demo.exception.RegraNegocioException;
import br.edu.ifsudestemg.demo.model.entity.Bomba;
import br.edu.ifsudestemg.demo.model.repository.BombaJpaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class BombaService {
    private BombaJpaRepository repository;

    public BombaService(BombaJpaRepository repository) {
        this.repository = repository;
    }

    public List<Bomba> getBomba() {
        return repository.findAll();
    }

    public List<Bomba> getBombaByPosto(Long idPosto) {
        return repository.findByPostoId(idPosto);
    }

    public Optional<Bomba> getBombaById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Bomba salvar(Bomba bomba){
        this.validar(bomba);
        return repository.save(bomba);
    }

    @Transactional
    public void excluir(Bomba bomba){
        Objects.requireNonNull(bomba.getId());
        repository.delete(bomba);
    }

    public void validarString(String valor, String mensagem){
        if(valor == null || valor.isBlank()){
            throw new RegraNegocioException(mensagem);
        }
    }

    public void validarNString(Object valor, String mensagem){
        if(valor == null){
            throw new RegraNegocioException(mensagem);
        }
    }

    public void validar(Bomba bomba){
        validarString(bomba.getCodigo(), "Codigo invalido");
        validarNString(bomba.getPosto(), "Posto invalido");
        validarNString(bomba.getAtivo(), "Ativo bomba invalido");
        if (bomba.getCombustiveis() == null || bomba.getCombustiveis().isEmpty()) {
            throw new RegraNegocioException("Combustiveis invalidos");
        }
    }
}
