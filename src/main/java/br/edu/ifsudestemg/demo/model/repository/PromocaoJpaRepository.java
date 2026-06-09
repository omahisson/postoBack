package br.edu.ifsudestemg.demo.model.repository;

import br.edu.ifsudestemg.demo.model.entity.Promocao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PromocaoJpaRepository extends JpaRepository<Promocao, Long> {
    List<Promocao> findByProdutosId(Long produtoId);
    List<Promocao> findByServicosId(Long servicoId);
}
