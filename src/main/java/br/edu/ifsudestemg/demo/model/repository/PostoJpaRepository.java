package br.edu.ifsudestemg.demo.model.repository;

import br.edu.ifsudestemg.demo.model.entity.Posto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostoJpaRepository extends JpaRepository<Posto, Long> {
}
