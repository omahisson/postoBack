package br.edu.ifsudestemg.demo.model.repository;

import br.edu.ifsudestemg.demo.model.entity.Venda;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendaJpaRepository extends JpaRepository<Venda, Long> {
}
