package br.edu.ifsudestemg.demo.model.repository;

import br.edu.ifsudestemg.demo.model.entity.PdvTurno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PdvTurnoJpaRepository extends JpaRepository<PdvTurno, Long> {
    List<PdvTurno> findByPostoIdAndStatusIgnoreCase(Long idPosto, String status);
}
