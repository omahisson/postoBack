package br.edu.ifsudestemg.demo.model.repository;

import br.edu.ifsudestemg.demo.model.entity.Bomba;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BombaJpaRepository extends JpaRepository<Bomba, Long> {
    List<Bomba> findByPostoId(Long idPosto);
}
