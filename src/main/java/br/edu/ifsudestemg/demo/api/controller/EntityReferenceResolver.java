package br.edu.ifsudestemg.demo.api.controller;

import br.edu.ifsudestemg.demo.exception.RegraNegocioException;
import br.edu.ifsudestemg.demo.model.entity.Posto;
import br.edu.ifsudestemg.demo.model.entity.Produto;
import br.edu.ifsudestemg.demo.model.entity.Servico;
import br.edu.ifsudestemg.demo.model.repository.PostoJpaRepository;
import br.edu.ifsudestemg.demo.model.repository.ProdutoJpaRepository;
import br.edu.ifsudestemg.demo.model.repository.ServicoJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
class EntityReferenceResolver {
    private final PostoJpaRepository postoRepository;
    private final ProdutoJpaRepository produtoRepository;
    private final ServicoJpaRepository servicoRepository;

    Posto buscarPosto(Long idPosto) {
        if (idPosto == null) {
            throw new RegraNegocioException("Posto deve ser informado.");
        }
        return postoRepository.findById(idPosto)
                .orElseThrow(() -> new RegraNegocioException("Posto nao encontrado."));
    }

    Produto buscarProduto(Long idProduto) {
        if (idProduto == null) {
            throw new RegraNegocioException("Produto deve ser informado.");
        }
        return produtoRepository.findById(idProduto)
                .orElseThrow(() -> new RegraNegocioException("Produto nao encontrado."));
    }

    Servico buscarServico(Long idServico) {
        if (idServico == null) {
            throw new RegraNegocioException("Servico deve ser informado.");
        }
        return servicoRepository.findById(idServico)
                .orElseThrow(() -> new RegraNegocioException("Servico nao encontrado."));
    }

    List<Produto> buscarProdutos(List<Long> idsProdutos) {
        if (idsProdutos == null || idsProdutos.isEmpty()) {
            return List.of();
        }
        List<Produto> produtos = produtoRepository.findAllById(idsProdutos);
        if (produtos.size() != idsProdutos.size()) {
            throw new RegraNegocioException("Um ou mais produtos informados nao foram encontrados.");
        }
        return produtos;
    }

    List<Servico> buscarServicos(List<Long> idsServicos) {
        if (idsServicos == null || idsServicos.isEmpty()) {
            return List.of();
        }
        List<Servico> servicos = servicoRepository.findAllById(idsServicos);
        if (servicos.size() != idsServicos.size()) {
            throw new RegraNegocioException("Um ou mais servicos informados nao foram encontrados.");
        }
        return servicos;
    }
}
