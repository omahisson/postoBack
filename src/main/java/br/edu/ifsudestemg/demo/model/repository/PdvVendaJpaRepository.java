package br.edu.ifsudestemg.demo.model.repository;

import br.edu.ifsudestemg.demo.model.entity.PdvVenda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PdvVendaJpaRepository extends JpaRepository<PdvVenda, Long> {
    List<PdvVenda> findByTurnoIdOrderByDataDesc(Long idTurno);
    List<PdvVenda> findByPostoId(Long idPosto);
}
