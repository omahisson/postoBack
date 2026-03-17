package br.edu.ifsudestemg.demo.model.repository;

import br.edu.ifsudestemg.demo.model.entity.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FornecedorJpaRepository extends JpaRepository<Fornecedor, Long> {
}
