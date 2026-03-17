package br.edu.ifsudestemg.demo.model.repository;

import br.edu.ifsudestemg.demo.model.entity.Promocao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromocaoJpaRepository extends JpaRepository<Promocao, Long> {
}
