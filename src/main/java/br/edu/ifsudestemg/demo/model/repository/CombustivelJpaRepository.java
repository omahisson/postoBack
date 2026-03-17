package br.edu.ifsudestemg.demo.model.repository;

import br.edu.ifsudestemg.demo.model.entity.Combustivel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CombustivelJpaRepository extends JpaRepository<Combustivel, Long> {
}
