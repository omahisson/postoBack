package br.edu.ifsudestemg.demo.model.repository;

import br.edu.ifsudestemg.demo.model.entity.Servico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServicoJpaRepository extends JpaRepository<Servico, Long> {
    List<Servico> findByPostoId(Long idPosto);
}
