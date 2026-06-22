package br.edu.ifsudestemg.demo.model.repository;

import br.edu.ifsudestemg.demo.model.entity.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FuncionarioJpaRepository extends JpaRepository<Funcionario, Long> {
    Optional<Funcionario> findByMaticula(String maticula);

    boolean existsByMaticula(String maticula);

    Optional<Funcionario> findByEmail(String email);
}
