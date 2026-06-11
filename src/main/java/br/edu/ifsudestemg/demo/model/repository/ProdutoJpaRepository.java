package br.edu.ifsudestemg.demo.model.repository;

import br.edu.ifsudestemg.demo.model.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProdutoJpaRepository extends JpaRepository<Produto, Long> {
    @Query("select p from Produto p where type(p) = Produto")
    List<Produto> findProdutos();

    @Query("select p from Produto p where type(p) = Produto and p.posto.id = :idPosto")
    List<Produto> findProdutosByPostoId(@Param("idPosto") Long idPosto);

    @Query("select p from Produto p where p.posto.id = :idPosto")
    List<Produto> findByPostoId(@Param("idPosto") Long idPosto);
}
