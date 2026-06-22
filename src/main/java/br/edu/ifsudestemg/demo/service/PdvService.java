package br.edu.ifsudestemg.demo.service;

import br.edu.ifsudestemg.demo.api.dto.PdvFechamentoDTO;
import br.edu.ifsudestemg.demo.api.dto.PdvTurnoDTO;
import br.edu.ifsudestemg.demo.api.dto.PdvVendaDTO;
import br.edu.ifsudestemg.demo.api.dto.PdvVendaItemDTO;
import br.edu.ifsudestemg.demo.exception.RegraNegocioException;
import br.edu.ifsudestemg.demo.model.entity.*;
import br.edu.ifsudestemg.demo.model.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PdvService {
    private final PdvTurnoJpaRepository turnoRepository;
    private final PdvVendaJpaRepository vendaRepository;
    private final PostoJpaRepository postoRepository;
    private final FuncionarioJpaRepository funcionarioRepository;
    private final ProdutoJpaRepository produtoRepository;
    private final CombustivelJpaRepository combustivelRepository;
    private final ServicoJpaRepository servicoRepository;

    @Transactional
    public PdvTurno abrirTurno(PdvTurnoDTO dto) {
        if (dto == null) {
            throw new RegraNegocioException("Dados do turno devem ser informados");
        }
        Posto posto = buscarPosto(dto.getIdPosto());
        Funcionario operador = buscarFuncionario(dto.getOperadorId());
        if (turnoRepository.existsByPostoIdAndOperadorIdAndStatusIgnoreCase(posto.getId(), operador.getId(), "aberto")) {
            throw new RegraNegocioException("Ja existe turno aberto para este operador neste posto");
        }
        PdvTurno turno = new PdvTurno();
        turno.setPosto(posto);
        turno.setOperador(operador);
        turno.setOperadorNome(dto.getOperadorNome() == null || dto.getOperadorNome().isBlank() ? operador.getNome() : dto.getOperadorNome());
        turno.setTurno(obrigatorio(dto.getTurno(), "Turno invalido"));
        turno.setHoraAbertura(dto.getHoraAberturaISO() == null ? LocalDateTime.now() : dto.getHoraAberturaISO());
        turno.setValorInicialCaixa(numero(dto.getValorInicialCaixa()));
        turno.setStatus("aberto");
        turno.setTotalVendas(BigDecimal.ZERO);
        turno.setTotalCartao(BigDecimal.ZERO);
        turno.setTotalDinheiro(BigDecimal.ZERO);
        turno.setTotalTransacoes(0);
        return turnoRepository.save(turno);
    }

    public PdvTurno buscarTurno(Long id) {
        if (id == null) {
            throw new RegraNegocioException("Turno deve ser informado");
        }
        return turnoRepository.findById(id).orElseThrow(() -> new RegraNegocioException("Turno nao encontrado"));
    }

    public List<PdvTurno> buscarTurnosAbertos(Long idPosto) {
        if (idPosto == null) {
            throw new RegraNegocioException("Posto deve ser informado");
        }
        return turnoRepository.findByPostoIdAndStatusIgnoreCase(idPosto, "aberto");
    }

    public List<PdvVenda> listarVendasDoTurno(Long idTurno) {
        if (idTurno == null) {
            throw new RegraNegocioException("Turno deve ser informado");
        }
        return vendaRepository.findByTurnoIdOrderByDataDesc(idTurno);
    }

    public PdvVenda buscarVenda(Long id) {
        if (id == null) {
            throw new RegraNegocioException("Venda deve ser informada");
        }
        return vendaRepository.findById(id).orElseThrow(() -> new RegraNegocioException("Venda nao encontrada"));
    }

    @Transactional
    public PdvVenda registrarVenda(PdvVendaDTO dto) {
        if (dto == null) {
            throw new RegraNegocioException("Dados da venda devem ser informados");
        }
        PdvTurno turno = buscarTurno(dto.getIdTurno());
        if (!"aberto".equalsIgnoreCase(turno.getStatus())) {
            throw new RegraNegocioException("Turno fechado");
        }
        if (dto.getItens() == null || dto.getItens().isEmpty()) {
            throw new RegraNegocioException("Venda sem itens");
        }
        PdvVenda venda = new PdvVenda();
        venda.setPosto(turno.getPosto());
        venda.setTurno(turno);
        venda.setData(dto.getData() == null ? LocalDateTime.now() : dto.getData());
        venda.setFormaPagamento(obrigatorio(dto.getFormaPagamento(), "Forma de pagamento invalida"));
        venda.setItens(dto.getItens().stream().map(item -> montarItemVenda(item, turno.getPosto())).toList());
        venda.setTotal(venda.getItens().stream().map(PdvVendaItem::getValorTotal).filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add));
        venda.setCancelada(false);
        venda.getItens().forEach(item -> aplicarEstoque(item, false));
        return vendaRepository.save(venda);
    }

    @Transactional
    public PdvVenda cancelarVenda(Long id, String motivo) {
        PdvVenda venda = buscarVenda(id);
        if (Boolean.TRUE.equals(venda.getCancelada())) {
            return venda;
        }
        venda.setCancelada(true);
        venda.setCanceladaEm(LocalDateTime.now());
        venda.setMotivoCancelamento(motivo == null || motivo.isBlank() ? "Cancelada no PDV" : motivo);
        venda.getItens().forEach(item -> aplicarEstoque(item, true));
        return vendaRepository.save(venda);
    }

    @Transactional
    public PdvTurno fecharTurno(Long id, PdvFechamentoDTO dto) {
        PdvTurno turno = buscarTurno(id);
        if (dto == null) {
            throw new RegraNegocioException("Dados do fechamento devem ser informados");
        }
        if (!"aberto".equalsIgnoreCase(turno.getStatus())) {
            throw new RegraNegocioException("Turno ja fechado");
        }
        List<PdvVenda> vendas = listarVendasDoTurno(id).stream().filter(venda -> !Boolean.TRUE.equals(venda.getCancelada())).toList();
        BigDecimal totalVendas = vendas.stream().map(PdvVenda::getTotal).filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalCartao = totalPorPagamento(vendas, "cartao");
        BigDecimal totalDinheiro = totalPorPagamento(vendas, "dinheiro");
        BigDecimal valorEsperado = dto.getValorEsperado() == null ? numero(turno.getValorInicialCaixa()).add(totalDinheiro) : dto.getValorEsperado();
        BigDecimal valorFinal = numero(dto.getValorFinalCaixa());
        turno.setStatus("fechado");
        turno.setHoraFechamento(LocalDateTime.now());
        turno.setValorFinalCaixa(valorFinal);
        turno.setValorEsperado(valorEsperado);
        turno.setDiferenca(dto.getDiferenca() == null ? valorFinal.subtract(valorEsperado) : dto.getDiferenca());
        turno.setTotalVendas(totalVendas);
        turno.setTotalCartao(totalCartao);
        turno.setTotalDinheiro(totalDinheiro);
        turno.setTotalTransacoes(vendas.size());
        return turnoRepository.save(turno);
    }

    private BigDecimal totalPorPagamento(List<PdvVenda> vendas, String formaPagamento) {
        return vendas.stream()
                .filter(venda -> venda.getFormaPagamento() != null && venda.getFormaPagamento().equalsIgnoreCase(formaPagamento))
                .map(PdvVenda::getTotal)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private PdvVendaItem montarItemVenda(PdvVendaItemDTO dto, Posto posto) {
        String tipo = obrigatorio(dto.getTipo(), "Tipo do item invalido").toLowerCase();
        if (dto.getItemId() == null) {
            throw new RegraNegocioException("Item invalido");
        }
        BigDecimal quantidade = numero(dto.getQuantidade());
        if (quantidade.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RegraNegocioException("Quantidade invalida");
        }

        if ("servico".equals(tipo)) {
            Servico servico = servicoRepository.findById(dto.getItemId())
                    .orElseThrow(() -> new RegraNegocioException("Servico nao encontrado"));
            validarMesmoPosto(servico.getPosto(), posto);
            return new PdvVendaItem("servico", servico.getId(), servico.getNome(), quantidade,
                    numero(servico.getPreco()), numero(servico.getPreco()).multiply(quantidade), servico.getUnidade());
        }

        Produto produto;
        if ("combustivel".equals(tipo)) {
            produto = combustivelRepository.findById(dto.getItemId())
                    .orElseThrow(() -> new RegraNegocioException("Combustivel nao encontrado"));
        } else if ("produto".equals(tipo)) {
            produto = produtoRepository.findById(dto.getItemId())
                    .orElseThrow(() -> new RegraNegocioException("Produto nao encontrado"));
        } else {
            throw new RegraNegocioException("Tipo do item invalido");
        }
        validarMesmoPosto(produto.getPosto(), posto);
        return new PdvVendaItem(tipo, produto.getId(), produto.getNome(), quantidade,
                numero(produto.getPreco()), numero(produto.getPreco()).multiply(quantidade), produto.getUnidade());
    }

    private void aplicarEstoque(PdvVendaItem item, boolean devolver) {
        if (item.getTipo() == null || item.getItemId() == null || item.getQuantidade() == null) {
            return;
        }
        if ("servico".equalsIgnoreCase(item.getTipo())) {
            return;
        }
        Produto produto = "combustivel".equalsIgnoreCase(item.getTipo())
                ? combustivelRepository.findById(item.getItemId()).orElseThrow(() -> new RegraNegocioException("Combustivel nao encontrado"))
                : produtoRepository.findById(item.getItemId()).orElseThrow(() -> new RegraNegocioException("Produto nao encontrado"));
        BigDecimal estoque = produto.getEstoque() == null ? BigDecimal.ZERO : produto.getEstoque();
        BigDecimal atualizado = devolver ? estoque.add(item.getQuantidade()) : estoque.subtract(item.getQuantidade());
        if (!devolver && atualizado.compareTo(BigDecimal.ZERO) < 0) {
            throw new RegraNegocioException("Estoque insuficiente para " + produto.getNome());
        }
        produto.setEstoque(atualizado);
        produtoRepository.save(produto);
    }

    private Posto buscarPosto(Long idPosto) {
        if (idPosto == null) {
            throw new RegraNegocioException("Posto invalido");
        }
        return postoRepository.findById(idPosto).orElseThrow(() -> new RegraNegocioException("Posto nao encontrado"));
    }

    private Funcionario buscarFuncionario(Long idFuncionario) {
        if (idFuncionario == null) {
            throw new RegraNegocioException("Funcionario invalido");
        }
        return funcionarioRepository.findById(idFuncionario).orElseThrow(() -> new RegraNegocioException("Funcionario nao encontrado"));
    }

    private String obrigatorio(String valor, String mensagem) {
        if (valor == null || valor.isBlank()) {
            throw new RegraNegocioException(mensagem);
        }
        return valor;
    }

    private BigDecimal numero(BigDecimal valor) {
        return valor == null ? BigDecimal.ZERO : valor;
    }

    private void validarMesmoPosto(Posto postoItem, Posto postoTurno) {
        if (postoItem == null || postoTurno == null || !Objects.equals(postoItem.getId(), postoTurno.getId())) {
            throw new RegraNegocioException("Item nao pertence ao posto do turno");
        }
    }
}
