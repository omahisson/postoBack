package br.edu.ifsudestemg.demo.model.repository;

import br.edu.ifsudestemg.demo.model.entity.Servico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicoJpaRepository extends JpaRepository<Servico, Long> {
}
