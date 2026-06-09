package br.edu.ifsudestemg.demo.service;

import br.edu.ifsudestemg.demo.model.entity.Produto;
import br.edu.ifsudestemg.demo.model.entity.Promocao;
import br.edu.ifsudestemg.demo.model.entity.RegistroPrecoProduto;
import br.edu.ifsudestemg.demo.model.entity.RegistroPrecoServico;
import br.edu.ifsudestemg.demo.model.entity.Servico;
import br.edu.ifsudestemg.demo.model.repository.PromocaoJpaRepository;
import br.edu.ifsudestemg.demo.model.repository.RegistroPrecoProdutoJpaRepository;
import br.edu.ifsudestemg.demo.model.repository.RegistroPrecoServicoJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DeleteDependencyCleaner {
    private final PromocaoJpaRepository promocaoRepository;
    private final RegistroPrecoProdutoJpaRepository registroPrecoProdutoRepository;
    private final RegistroPrecoServicoJpaRepository registroPrecoServicoRepository;

    public void limparVinculosDoProduto(Produto produto) {
        Long produtoId = produto.getId();
        List<Promocao> promocoes = promocaoRepository.findByProdutosId(produtoId);
        promocoes.forEach(promocao -> promocao.getProdutos().removeIf(item -> item.getId().equals(produtoId)));
        promocaoRepository.saveAll(promocoes);

        List<RegistroPrecoProduto> registros = registroPrecoProdutoRepository.findByProdutoId(produtoId);
        registroPrecoProdutoRepository.deleteAll(registros);
    }

    public void limparVinculosDoServico(Servico servico) {
        Long servicoId = servico.getId();
        List<Promocao> promocoes = promocaoRepository.findByServicosId(servicoId);
        promocoes.forEach(promocao -> promocao.getServicos().removeIf(item -> item.getId().equals(servicoId)));
        promocaoRepository.saveAll(promocoes);

        List<RegistroPrecoServico> registros = registroPrecoServicoRepository.findByServicoId(servicoId);
        registroPrecoServicoRepository.deleteAll(registros);
    }
}
