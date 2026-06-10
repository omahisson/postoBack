package br.edu.ifsudestemg.demo.service;

import br.edu.ifsudestemg.demo.api.dto.HistoricoCombustivelDTO;
import br.edu.ifsudestemg.demo.exception.RegraNegocioException;
import br.edu.ifsudestemg.demo.model.entity.Combustivel;
import br.edu.ifsudestemg.demo.model.entity.HistoricoCombustivel;
import br.edu.ifsudestemg.demo.model.repository.CombustivelJpaRepository;
import br.edu.ifsudestemg.demo.model.repository.HistoricoCombustivelJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HistoricoCombustivelService {
    private final HistoricoCombustivelJpaRepository repository;
    private final CombustivelJpaRepository combustivelRepository;

    public List<HistoricoCombustivel> getHistoricos(Long idPosto, Long idCombustivel) {
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

    public Optional<HistoricoCombustivel> getHistoricoById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public HistoricoCombustivel alterarPreco(Combustivel combustivel, HistoricoCombustivelDTO dto) {
        validarAlteracao(combustivel, dto);
        HistoricoCombustivel historico = new HistoricoCombustivel();
        historico.setPosto(combustivel.getPosto());
        historico.setCombustivel(combustivel);
        historico.setTipoCombustivel(dto.getTipoCombustivel() == null || dto.getTipoCombustivel().isBlank() ? combustivel.getNome() : dto.getTipoCombustivel());
        historico.setPrecoAnterior(combustivel.getPreco());
        historico.setNovoPreco(dto.getNovoPreco());
        historico.setDataVigencia(dto.getDataVigencia());
        historico.setResponsavel(dto.getResponsavel());
        historico.setMotivo(dto.getMotivo());
        historico.setDataAlteracao(LocalDate.now());
        if (dto.getNome() != null && !dto.getNome().isBlank()) {
            combustivel.setNome(dto.getNome());
            historico.setTipoCombustivel(dto.getNome());
        }
        if (dto.getFornecedor() != null && !dto.getFornecedor().isBlank()) {
            combustivel.setFornecedor(dto.getFornecedor());
        }
        if (dto.getEstoque() != null) {
            combustivel.setEstoque(dto.getEstoque());
        }
        combustivel.setPreco(dto.getNovoPreco());
        combustivelRepository.save(combustivel);
        return repository.save(historico);
    }

    @Transactional
    public void excluir(HistoricoCombustivel historico) {
        Objects.requireNonNull(historico.getId());
        repository.delete(historico);
    }

    private void validarAlteracao(Combustivel combustivel, HistoricoCombustivelDTO dto) {
        if (combustivel == null) {
            throw new RegraNegocioException("Combustivel invalido");
        }
        if (dto.getNovoPreco() == null || dto.getNovoPreco().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RegraNegocioException("Novo preco invalido");
        }
        if (dto.getDataVigencia() == null) {
            throw new RegraNegocioException("Data de vigencia invalida");
        }
        if (dto.getResponsavel() == null || dto.getResponsavel().isBlank()) {
            throw new RegraNegocioException("Responsavel invalido");
        }
        if (dto.getMotivo() == null || dto.getMotivo().isBlank()) {
            throw new RegraNegocioException("Motivo invalido");
        }
    }
}
