package br.edu.ifsudestemg.demo.service;

import br.edu.ifsudestemg.demo.model.entity.Combustivel;
import br.edu.ifsudestemg.demo.model.entity.HistoricoCombustivel;
import br.edu.ifsudestemg.demo.model.entity.Posto;
import br.edu.ifsudestemg.demo.model.entity.Produto;
import br.edu.ifsudestemg.demo.model.entity.Venda;
import br.edu.ifsudestemg.demo.model.repository.CombustivelJpaRepository;
import br.edu.ifsudestemg.demo.model.repository.HistoricoCombustivelJpaRepository;
import br.edu.ifsudestemg.demo.model.repository.PostoJpaRepository;
import br.edu.ifsudestemg.demo.model.repository.ProdutoJpaRepository;
import br.edu.ifsudestemg.demo.model.repository.VendaJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class DashboardService {
    private final VendaJpaRepository vendaRepository;
    private final PostoJpaRepository postoRepository;
    private final ProdutoJpaRepository produtoRepository;
    private final CombustivelJpaRepository combustivelRepository;
    private final HistoricoCombustivelJpaRepository historicoCombustivelRepository;

    public Map<String, Object> getDashboard(Long idPosto) {
        List<Venda> vendas = idPosto == null ? vendaRepository.findAll() : vendaRepository.findByPostoId(idPosto);
        List<Posto> postos = postoRepository.findAll();
        List<Produto> produtos = idPosto == null ? produtoRepository.findAll() : produtoRepository.findByPostoId(idPosto);
        List<Combustivel> combustiveis = idPosto == null ? combustivelRepository.findAll() : combustivelRepository.findByPostoId(idPosto);
        List<HistoricoCombustivel> historicos = idPosto == null ? historicoCombustivelRepository.findAll() : historicoCombustivelRepository.findByPostoId(idPosto);

        Map<String, Object> dashboard = new LinkedHashMap<>();
        dashboard.put("dadosFinanceiros", dadosFinanceiros(vendas));
        dashboard.put("rankingRede", rankingRede(postos));
        dashboard.put("maisVendidos", maisVendidos(produtos, combustiveis));
        dashboard.put("relatorioPreco", relatorioPreco(historicos));
        dashboard.put("relatorioEstoque", relatorioEstoque(produtos, combustiveis));
        dashboard.put("relatorioOperacional", relatorioOperacional(vendas, produtos, combustiveis));
        return dashboard;
    }

    private Map<String, Object> dadosFinanceiros(List<Venda> vendas) {
        BigDecimal faturamento = vendas.stream()
                .map(Venda::getValorLiquido)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal ticketMedio = vendas.isEmpty() ? BigDecimal.ZERO : faturamento.divide(BigDecimal.valueOf(vendas.size()), 2, RoundingMode.HALF_UP);
        String pagamentoPrincipal = vendas.stream()
                .filter(venda -> venda.getFormaPagamento() != null)
                .collect(java.util.stream.Collectors.groupingBy(Venda::getFormaPagamento, java.util.stream.Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(entry -> entry.getKey().name())
                .orElse("N/A");

        Map<String, Object> dados = new LinkedHashMap<>();
        dados.put("faturamentoTotal", moeda(faturamento));
        dados.put("ticketMedio", moeda(ticketMedio));
        dados.put("totalVendas", vendas.size());
        dados.put("mainPayment", pagamentoPrincipal);
        return dados;
    }

    private List<Map<String, Object>> rankingRede(List<Posto> postos) {
        List<Venda> vendas = vendaRepository.findAll();
        return postos.stream()
                .map(posto -> {
                    BigDecimal revenue = vendas.stream()
                            .filter(venda -> venda.getPosto() != null && venda.getPosto().getId().equals(posto.getId()))
                            .map(Venda::getValorLiquido)
                            .filter(Objects::nonNull)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                    Map<String, Object> item = new LinkedHashMap<>();
                    item.put("id", posto.getId());
                    item.put("name", posto.getNomeFantasia() == null ? posto.getNome() : posto.getNomeFantasia());
                    item.put("revenue", moeda(revenue));
                    return item;
                })
                .sorted((a, b) -> valorMoeda(b.get("revenue")).compareTo(valorMoeda(a.get("revenue"))))
                .toList();
    }

    private List<Map<String, Object>> maisVendidos(List<Produto> produtos, List<Combustivel> combustiveis) {
        return java.util.stream.Stream.concat(produtos.stream(), combustiveis.stream())
                .sorted(Comparator.comparing(item -> item.getPreco() == null ? BigDecimal.ZERO : item.getPreco(), Comparator.reverseOrder()))
                .limit(5)
                .map(item -> {
                    Map<String, Object> row = new LinkedHashMap<>();
                    row.put("name", item.getNome());
                    row.put("quantity", item.getEstoque() == null ? 0 : item.getEstoque());
                    row.put("revenue", moeda(item.getPreco() == null ? BigDecimal.ZERO : item.getPreco()));
                    return row;
                })
                .toList();
    }

    private List<Map<String, Object>> relatorioPreco(List<HistoricoCombustivel> historicos) {
        return historicos.stream()
                .sorted(Comparator.comparing(HistoricoCombustivel::getDataAlteracao, Comparator.nullsLast(Comparator.reverseOrder())))
                .limit(20)
                .map(historico -> {
                    Map<String, Object> row = new LinkedHashMap<>();
                    row.put("product", historico.getTipoCombustivel());
                    row.put("responsible", historico.getResponsavel());
                    row.put("prevPrice", moeda(historico.getPrecoAnterior()));
                    row.put("newPrice", moeda(historico.getNovoPreco()));
                    row.put("date", data(historico.getDataAlteracao()));
                    row.put("effective", data(historico.getDataVigencia()));
                    return row;
                })
                .toList();
    }

    private List<Map<String, Object>> relatorioEstoque(List<Produto> produtos, List<Combustivel> combustiveis) {
        return java.util.stream.Stream.concat(produtos.stream(), combustiveis.stream())
                .map(item -> {
                    int estoque = item.getEstoque() == null ? 0 : item.getEstoque();
                    Map<String, Object> row = new LinkedHashMap<>();
                    row.put("item", item.getNome());
                    row.put("quantity", estoque);
                    row.put("minStock", 10);
                    row.put("status", statusEstoque(estoque, item.getDataValidade()));
                    row.put("expiry", data(item.getDataValidade()));
                    return row;
                })
                .toList();
    }

    private Map<String, Object> relatorioOperacional(List<Venda> vendas, List<Produto> produtos, List<Combustivel> combustiveis) {
        Map<String, Object> dados = new LinkedHashMap<>();
        dados.put("vendas", vendas.size());
        dados.put("produtos", produtos.size());
        dados.put("combustiveis", combustiveis.size());
        return dados;
    }

    private String statusEstoque(int estoque, LocalDate validade) {
        if (validade != null && validade.isBefore(LocalDate.now().plusDays(30))) {
            return "Vencimento";
        }
        if (estoque <= 0) {
            return "Critico";
        }
        if (estoque < 10) {
            return "Baixo";
        }
        return "OK";
    }

    private String moeda(BigDecimal valor) {
        BigDecimal seguro = valor == null ? BigDecimal.ZERO : valor;
        return NumberFormat.getCurrencyInstance(Locale.forLanguageTag("pt-BR")).format(seguro);
    }

    private BigDecimal valorMoeda(Object valor) {
        String texto = String.valueOf(valor).replace("R$", "").replace(".", "").replace(",", ".").trim();
        try {
            return new BigDecimal(texto);
        } catch (NumberFormatException e) {
            return BigDecimal.ZERO;
        }
    }

    private String data(LocalDate data) {
        return data == null ? "" : data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
}
