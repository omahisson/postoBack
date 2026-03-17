package br.edu.ifsudestemg.demo.model.repository;

import br.edu.ifsudestemg.demo.model.entity.Turno;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TurnoJpaRepository extends JpaRepository<Turno, Long> {
}
