package br.edu.ifsudestemg.demo.model.repository;

import br.edu.ifsudestemg.demo.model.entity.RegistroPrecoProduto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegistroPrecoProdutoJpaRepository extends JpaRepository<RegistroPrecoProduto, Long> {
    List<RegistroPrecoProduto> findByProdutoId(Long produtoId);
}
