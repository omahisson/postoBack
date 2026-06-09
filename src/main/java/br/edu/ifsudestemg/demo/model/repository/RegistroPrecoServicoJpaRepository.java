package br.edu.ifsudestemg.demo.model.repository;

import br.edu.ifsudestemg.demo.model.entity.RegistroPrecoServico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegistroPrecoServicoJpaRepository extends JpaRepository<RegistroPrecoServico, Long> {
    List<RegistroPrecoServico> findByServicoId(Long servicoId);
}
