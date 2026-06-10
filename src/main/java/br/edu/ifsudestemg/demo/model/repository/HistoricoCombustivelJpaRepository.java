package br.edu.ifsudestemg.demo.model.repository;

import br.edu.ifsudestemg.demo.model.entity.HistoricoCombustivel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoricoCombustivelJpaRepository extends JpaRepository<HistoricoCombustivel, Long> {
    List<HistoricoCombustivel> findByPostoId(Long idPosto);
    List<HistoricoCombustivel> findByCombustivelId(Long idCombustivel);
    List<HistoricoCombustivel> findByCombustivelIdAndPostoId(Long idCombustivel, Long idPosto);
}
