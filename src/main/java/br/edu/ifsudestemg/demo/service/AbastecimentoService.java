package br.edu.ifsudestemg.demo.service;

import br.edu.ifsudestemg.demo.exception.RegraNegocioException;
import br.edu.ifsudestemg.demo.model.entity.Abastecimento;
import br.edu.ifsudestemg.demo.model.entity.Combustivel;
import br.edu.ifsudestemg.demo.model.repository.AbastecimentoJpaRepository;
import br.edu.ifsudestemg.demo.model.repository.CombustivelJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AbastecimentoService {
    private final AbastecimentoJpaRepository repository;
    private final CombustivelJpaRepository combustivelRepository;

    public List<Abastecimento> getAbastecimentos(Long idPosto, Long idCombustivel) {
        if (idPosto != null && idCombustivel != null) {
            return repository.findByCombustivelIdAndPostoId(idCombustivel, idPosto);
        }
        if (idPosto != null) {
            return repository.findByPostoId(idPosto);
        }
        if (idCombustivel != null) {
            return repository.findByCombustivelId(idCombustivel);
        }
        return repository.findAll();
    }

    public Optional<Abastecimento> getAbastecimentoById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Abastecimento salvar(Abastecimento abastecimento) {
        validar(abastecimento);
        Optional<Abastecimento> anterior = abastecimento.getId() == null ? Optional.empty() : repository.findById(abastecimento.getId());
        abastecimento.setValorTotal(abastecimento.getQuantidade().multiply(abastecimento.getPrecoUnitario()).setScale(2, RoundingMode.HALF_UP));
        anterior.ifPresent(this::reverterEstoque);
        aplicarEstoque(abastecimento);
        return repository.save(abastecimento);
    }

    @Transactional
    public void excluir(Abastecimento abastecimento) {
        Objects.requireNonNull(abastecimento.getId());
        reverterEstoque(abastecimento);
        repository.delete(abastecimento);
    }

    private void aplicarEstoque(Abastecimento abastecimento) {
        Combustivel combustivel = abastecimento.getCombustivel();
        int estoqueAtual = combustivel.getEstoque() == null ? 0 : combustivel.getEstoque();
        combustivel.setEstoque(estoqueAtual + abastecimento.getQuantidade().intValue());
        combustivel.setFornecedor(abastecimento.getFornecedor());
        combustivel.setDataValidade(abastecimento.getDataValidade());
        combustivel.setUltimoAbastecimento(abastecimento.getDataEntrega() == null ? null : abastecimento.getDataEntrega().toString());
        combustivelRepository.save(combustivel);
    }

    private void reverterEstoque(Abastecimento abastecimento) {
        Combustivel combustivel = abastecimento.getCombustivel();
        if (combustivel == null || abastecimento.getQuantidade() == null) {
            return;
        }
        int estoqueAtual = combustivel.getEstoque() == null ? 0 : combustivel.getEstoque();
        combustivel.setEstoque(Math.max(0, estoqueAtual - abastecimento.getQuantidade().intValue()));
        combustivelRepository.save(combustivel);
    }

    private void validar(Abastecimento abastecimento) {
        if (abastecimento.getPosto() == null) {
            throw new RegraNegocioException("Posto invalido");
        }
        if (abastecimento.getCombustivel() == null) {
            throw new RegraNegocioException("Combustivel invalido");
        }
        if (abastecimento.getQuantidade() == null || abastecimento.getQuantidade().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RegraNegocioException("Quantidade invalida");
        }
        if (abastecimento.getPrecoUnitario() == null || abastecimento.getPrecoUnitario().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RegraNegocioException("Preco unitario invalido");
        }
        if (abastecimento.getFornecedor() == null || abastecimento.getFornecedor().isBlank()) {
            throw new RegraNegocioException("Fornecedor invalido");
        }
        if (abastecimento.getUnidade() == null || abastecimento.getUnidade().isBlank()) {
            throw new RegraNegocioException("Unidade invalida");
        }
        if (abastecimento.getNumeroNota() == null || abastecimento.getNumeroNota().isBlank()) {
            throw new RegraNegocioException("Numero da nota invalido");
        }
        if (abastecimento.getDataEntrega() == null) {
            throw new RegraNegocioException("Data de entrega invalida");
        }
        if (abastecimento.getDataValidade() == null) {
            throw new RegraNegocioException("Data de validade invalida");
        }
    }
}
