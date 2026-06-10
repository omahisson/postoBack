package br.edu.ifsudestemg.demo.model.repository;

import br.edu.ifsudestemg.demo.model.entity.Abastecimento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AbastecimentoJpaRepository extends JpaRepository<Abastecimento, Long> {
    List<Abastecimento> findByPostoId(Long idPosto);
    List<Abastecimento> findByCombustivelId(Long idCombustivel);
    List<Abastecimento> findByCombustivelIdAndPostoId(Long idCombustivel, Long idPosto);
}
