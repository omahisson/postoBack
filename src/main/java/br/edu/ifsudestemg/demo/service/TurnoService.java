package br.edu.ifsudestemg.demo.service;

import br.edu.ifsudestemg.demo.model.entity.Turno;
import br.edu.ifsudestemg.demo.model.repository.TurnoJpaRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TurnoService {

    private final TurnoJpaRepository repository;

    public List<Turno> getTurnos(){
        return repository.findAll();
    }

    public Optional<Turno> getTurno(Long id){
        return repository.findById(id);
    }

    @Transactional
    public Turno salvar(Turno turno) {
        validar(turno);
        turno.setAtivo(true);
        return repository.save(turno);
    }

    @Transactional
    public void excluir(Turno turno) {
        Objects.requireNonNull(turno.getId());
        repository.delete(turno);
    }

    public void validar(Turno turno) {
        if(turno.getNome() == null || turno.getNome().trim().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nome inválido");
        }
        if(turno.getHoraInicio() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Hora de início inválida");
        }
        if(turno.getHoraFim() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Hora de fim inválida");
        }
        if(turno.getPosto() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Posto inválida");
        }
    }
}
