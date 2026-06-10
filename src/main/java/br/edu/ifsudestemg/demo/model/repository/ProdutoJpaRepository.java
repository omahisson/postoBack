package br.edu.ifsudestemg.demo.model.repository;

import br.edu.ifsudestemg.demo.model.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProdutoJpaRepository extends JpaRepository<Produto, Long> {
    List<Produto> findByPostoId(Long idPosto);
}
