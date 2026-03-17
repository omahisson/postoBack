package br.edu.ifsudestemg.demo.model.repository;

import br.edu.ifsudestemg.demo.model.entity.Compra;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompraJpaRepository extends JpaRepository<Compra, Long> {
}
