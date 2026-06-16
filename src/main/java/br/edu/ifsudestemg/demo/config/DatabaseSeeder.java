package br.edu.ifsudestemg.demo.config;

import br.edu.ifsudestemg.demo.infrastructuries.enums.FormaPagamento;
import br.edu.ifsudestemg.demo.infrastructuries.enums.Status;
import br.edu.ifsudestemg.demo.infrastructuries.enums.TipoDesconto;
import br.edu.ifsudestemg.demo.model.entity.Bomba;
import br.edu.ifsudestemg.demo.model.entity.Cliente;
import br.edu.ifsudestemg.demo.model.entity.Colaborador;
import br.edu.ifsudestemg.demo.model.entity.Combustivel;
import br.edu.ifsudestemg.demo.model.entity.Compra;
import br.edu.ifsudestemg.demo.model.entity.Fornecedor;
import br.edu.ifsudestemg.demo.model.entity.Funcionario;
import br.edu.ifsudestemg.demo.model.entity.Gerente;
import br.edu.ifsudestemg.demo.model.entity.Pessoa;
import br.edu.ifsudestemg.demo.model.entity.PessoaFisica;
import br.edu.ifsudestemg.demo.model.entity.PessoaJuridica;
import br.edu.ifsudestemg.demo.model.entity.Posto;
import br.edu.ifsudestemg.demo.model.entity.Produto;
import br.edu.ifsudestemg.demo.model.entity.Promocao;
import br.edu.ifsudestemg.demo.model.entity.RegistroPrecoProduto;
import br.edu.ifsudestemg.demo.model.entity.RegistroPrecoServico;
import br.edu.ifsudestemg.demo.model.entity.Servico;
import br.edu.ifsudestemg.demo.model.entity.Turno;
import br.edu.ifsudestemg.demo.model.entity.Venda;
import br.edu.ifsudestemg.demo.model.entity.Vendivel;
import br.edu.ifsudestemg.demo.model.repository.BombaJpaRepository;
import br.edu.ifsudestemg.demo.model.repository.ClienteJpaRepository;
import br.edu.ifsudestemg.demo.model.repository.CombustivelJpaRepository;
import br.edu.ifsudestemg.demo.model.repository.CompraJpaRepository;
import br.edu.ifsudestemg.demo.model.repository.FornecedorJpaRepository;
import br.edu.ifsudestemg.demo.model.repository.FuncionarioJpaRepository;
import br.edu.ifsudestemg.demo.model.repository.PostoJpaRepository;
import br.edu.ifsudestemg.demo.model.repository.ProdutoJpaRepository;
import br.edu.ifsudestemg.demo.model.repository.PromocaoJpaRepository;
import br.edu.ifsudestemg.demo.model.repository.RegistroPrecoProdutoJpaRepository;
import br.edu.ifsudestemg.demo.model.repository.RegistroPrecoServicoJpaRepository;
import br.edu.ifsudestemg.demo.model.repository.ServicoJpaRepository;
import br.edu.ifsudestemg.demo.model.repository.TurnoJpaRepository;
import br.edu.ifsudestemg.demo.model.repository.VendaJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DatabaseSeeder implements CommandLineRunner {

    private final PostoJpaRepository postoRepository;
    private final BombaJpaRepository bombaRepository;
    private final ClienteJpaRepository clienteRepository;
    private final FornecedorJpaRepository fornecedorRepository;
    private final FuncionarioJpaRepository funcionarioRepository;
    private final ProdutoJpaRepository produtoRepository;
    private final CombustivelJpaRepository combustivelRepository;
    private final ServicoJpaRepository servicoRepository;
    private final TurnoJpaRepository turnoRepository;
    private final CompraJpaRepository compraRepository;
    private final VendaJpaRepository vendaRepository;
    private final PromocaoJpaRepository promocaoRepository;
    private final RegistroPrecoProdutoJpaRepository registroPrecoProdutoRepository;
    private final RegistroPrecoServicoJpaRepository registroPrecoServicoRepository;

    @Override
    @Transactional
    public void run(String... args) {
        Posto postoCentro;
        Posto postoNorte;

        List<Posto> postos = postoRepository.findAll();
        if (postos.size() >= 2) {
            postoCentro = postos.get(0);
            postoNorte = postos.get(1);
        } else {
            postoCentro = postos.isEmpty() ? postoRepository.save(posto(
                    "Posto Marco Centro",
                    "11.111.111/0001-11",
                    "Posto Marco Centro LTDA",
                    "Marco Centro",
                    "MG-000111",
                    "Rua das Palmeiras",
                    "100",
                    "Centro",
                    "Juiz de Fora",
                    "MG",
                    "36010-000",
                    "(32) 3333-1000",
                    "centro@postomarco.com"
            )) : postos.get(0);
            postoNorte = postoRepository.save(posto(
                    "Posto Marco Norte",
                    "22.222.222/0001-22",
                    "Posto Marco Norte LTDA",
                    "Marco Norte",
                    "MG-000222",
                    "Avenida Brasil",
                    "2200",
                    "Industrial",
                    "Juiz de Fora",
                    "MG",
                    "36080-000",
                    "(32) 3333-2000",
                    "norte@postomarco.com"
            ));
            postoCentro.setPosto(postoCentro);
            postoNorte.setPosto(postoNorte);
            postoRepository.saveAll(List.of(postoCentro, postoNorte));
        }

        if (bombaRepository.count() == 0) {
            bombaRepository.saveAll(List.of(
                    bomba("BOMBA-01", "NS-BOMBA-0001", postoCentro),
                    bomba("BOMBA-02", "NS-BOMBA-0002", postoNorte)
            ));
        }

        if (clienteRepository.count() == 0) {
            clienteRepository.saveAll(List.of(
                    cliente("Ana Souza", "111.111.111-11", "MG-11.111.111", postoCentro, "ana.souza@email.com"),
                    cliente("Bruno Lima", "222.222.222-22", "MG-22.222.222", postoNorte, "bruno.lima@email.com")
            ));
        }

        if (fornecedorRepository.count() == 0) {
            fornecedorRepository.saveAll(List.of(
                    fornecedor("Distribuidora Alfa", "33.333.333/0001-33", "Alfa Combustiveis", postoCentro, "Marina Costa"),
                    fornecedor("LubriMax Atacado", "44.444.444/0001-44", "LubriMax", postoNorte, "Paulo Nunes")
            ));
        }

        if (funcionarioRepository.count() == 0) {
            funcionarioRepository.saveAll(List.of(
                    gerente("Carla Mendes", "333.333.333-33", postoCentro),
                    colaborador("Diego Alves", "444.444.444-44", postoNorte)
            ));
        }

        Produto oleo;
        Produto aditivo;
        List<Produto> produtos = produtoRepository.findAll();
        if (produtos.size() >= 2) {
            oleo = produtos.get(0);
            aditivo = produtos.get(1);
        } else {
            oleo = produtoRepository.save(produto("Oleo Motor 5W30", "7891000000011", "Lubrax", "Lubrificante", postoCentro, "UN", "89.90", "25"));
            aditivo = produtoRepository.save(produto("Aditivo Radiador", "7891000000028", "RadCool", "Aditivo", postoNorte, "UN", "24.90", "30"));
        }

        if (combustivelRepository.count() == 0) {
            combustivelRepository.saveAll(List.of(
                    combustivel("Gasolina Comum", "7892000000018", "PetroSul", "Gasolina", postoCentro, "L", "5.79", "150", "Gasolina", "87", "Gasolina C"),
                    combustivel("Etanol Hidratado", "7892000000025", "BioMinas", "Etanol", postoNorte, "L", "3.89", "120", "Etanol", "0", "Etanol hidratado")
            ));
        }

        Servico lavagem;
        Servico alinhamento;
        List<Servico> servicos = servicoRepository.findAll();
        if (servicos.size() >= 2) {
            lavagem = servicos.get(0);
            alinhamento = servicos.get(1);
        } else {
            lavagem = servicoRepository.save(servico("Lavagem simples", postoCentro, "35.00", "30", false));
            alinhamento = servicoRepository.save(servico("Alinhamento", postoNorte, "90.00", "45", true));
        }

        if (turnoRepository.count() == 0) {
            turnoRepository.saveAll(List.of(
                    turno("Manha", LocalTime.of(6, 0), LocalTime.of(14, 0), postoCentro),
                    turno("Tarde", LocalTime.of(14, 0), LocalTime.of(22, 0), postoNorte)
            ));
        }

        if (compraRepository.count() == 0) {
            compraRepository.saveAll(List.of(
                    compra(LocalDate.now().plusDays(1), "NF-1001", "12500.00", FormaPagamento.PIX, postoCentro, Status.PENDENTE),
                    compra(LocalDate.now().plusDays(2), "NF-1002", "8700.00", FormaPagamento.CARTAO_CREDITO, postoNorte, Status.PAGA)
            ));
        }

        if (vendaRepository.count() == 0) {
            vendaRepository.saveAll(List.of(
                    venda("250.00", "10.00", FormaPagamento.PIX, postoCentro, Status.PAGA),
                    venda("180.00", "0.00", FormaPagamento.DINHEIRO, postoNorte, Status.PAGA)
            ));
        }

        if (promocaoRepository.count() == 0) {
            promocaoRepository.saveAll(List.of(
                    promocao("Semana do oleo", "Desconto em lubrificantes selecionados", TipoDesconto.PERCENTUAL, "10.00", List.of(oleo), List.of(lavagem)),
                    promocao("Combo revisao", "Servico com preco especial", TipoDesconto.VALOR_FIXO, "20.00", List.of(aditivo), List.of(alinhamento))
            ));
        }

        if (registroPrecoProdutoRepository.count() == 0) {
            registroPrecoProdutoRepository.saveAll(List.of(
                    registroPrecoProduto(oleo, "89.90"),
                    registroPrecoProduto(aditivo, "24.90")
            ));
        }

        if (registroPrecoServicoRepository.count() == 0) {
            registroPrecoServicoRepository.saveAll(List.of(
                    registroPrecoServico(lavagem, "35.00"),
                    registroPrecoServico(alinhamento, "90.00")
            ));
        }
    }

    private Posto posto(String nome, String cnpj, String razaoSocial, String nomeFantasia, String inscricaoEstadual,
                       String logradouro, String numero, String bairro, String cidade, String estado, String cep,
                       String telefone, String email) {
        Posto posto = new Posto();
        pessoaJuridica(posto, nome, cnpj, razaoSocial, nomeFantasia, inscricaoEstadual, null, telefone, email,
                logradouro, numero, bairro, cidade, estado, cep);
        posto.setDataAbertura(LocalDate.now().minusYears(3));
        return posto;
    }

    private Bomba bomba(String codigo, String numeroSerie, Posto posto) {
        Bomba bomba = new Bomba();
        bomba.setCodigo(codigo);
        bomba.setNumeroSerie(numeroSerie);
        bomba.setPosto(posto);
        bomba.setAtivo(true);
        return bomba;
    }

    private Cliente cliente(String nome, String cpf, String rg, Posto posto, String email) {
        Cliente cliente = new Cliente();
        pessoaFisica(cliente, nome, cpf, rg, posto, "(32) 98888-0000", email);
        cliente.setLimiteCredito(new BigDecimal("500.00"));
        cliente.setPontosFidelidade(120L);
        cliente.setObservacoes("Cliente seed para testes");
        return cliente;
    }

    private Fornecedor fornecedor(String nome, String cnpj, String nomeFantasia, Posto posto, String contato) {
        Fornecedor fornecedor = new Fornecedor();
        pessoaJuridica(fornecedor, nome, cnpj, nome + " LTDA", nomeFantasia, "IE-" + cnpj.substring(0, 2), posto,
                "(32) 3777-0000", "contato@" + nomeFantasia.toLowerCase().replace(" ", "") + ".com",
                "Rua dos Fornecedores", "45", "Distrito", "Juiz de Fora", "MG", "36000-100");
        fornecedor.setContatoResponsavel(contato);
        fornecedor.setPrazoEntregaDias(3);
        fornecedor.setCategoriaFornecimento("Combustiveis e conveniencia");
        return fornecedor;
    }

    private Gerente gerente(String nome, String cpf, Posto posto) {
        Gerente gerente = new Gerente();
        funcionario(gerente, nome, cpf, posto, "GER-001", "Administracao");
        gerente.setBonusMeta(new BigDecimal("800.00"));
        return gerente;
    }

    private Colaborador colaborador(String nome, String cpf, Posto posto) {
        Colaborador colaborador = new Colaborador();
        funcionario(colaborador, nome, cpf, posto, "COL-001", "Pista");
        colaborador.setBonusMeta(BigDecimal.ZERO);
        return colaborador;
    }

    private Produto produto(String nome, String codigoBarras, String marca, String categoria, Posto posto, String unidade, String preco, String estoque) {
        Produto produto = new Produto();
        vendivel(produto, nome, posto, unidade, preco, "Produto seed para testes");
        produto.setCodigoBarras(codigoBarras);
        produto.setMarca(marca);
        produto.setCategoria(categoria);
        produto.setEstoque(new BigDecimal(estoque));
        produto.setDataValidade(LocalDate.now().plusMonths(6));
        return produto;
    }

    private Combustivel combustivel(String nome, String codigoBarras, String marca, String categoria, Posto posto,
                                   String unidade, String preco, String estoque, String tipo, String octanagem, String composicao) {
        Combustivel combustivel = new Combustivel();
        vendivel(combustivel, nome, posto, unidade, preco, "Combustivel seed para testes");
        combustivel.setCodigoBarras(codigoBarras);
        combustivel.setMarca(marca);
        combustivel.setCategoria(categoria);
        combustivel.setEstoque(new BigDecimal(estoque));
        combustivel.setDataValidade(LocalDate.now().plusMonths(6));
        combustivel.setTipoCombustivel(tipo);
        combustivel.setOctanagem(octanagem);
        combustivel.setComposicao(composicao);
        return combustivel;
    }

    private Servico servico(String nome, Posto posto, String preco, String duracao, Boolean requerAgendamento) {
        Servico servico = new Servico();
        vendivel(servico, nome, posto, "SERVICO", preco, "Servico seed para testes");
        servico.setDuracaoEstimadaMinutos(duracao);
        servico.setRequerAgendamento(requerAgendamento);
        servico.setDescricaoTecnica("Descricao tecnica seed");
        return servico;
    }

    private Turno turno(String nome, LocalTime inicio, LocalTime fim, Posto posto) {
        Turno turno = new Turno();
        turno.setNome(nome);
        turno.setHoraInicio(inicio);
        turno.setHoraFim(fim);
        turno.setPosto(posto);
        turno.setAtivo(true);
        return turno;
    }

    private Compra compra(LocalDate data, String notaFiscal, String valor, FormaPagamento formaPagamento, Posto posto, Status status) {
        Compra compra = new Compra();
        compra.setDataCompra(data);
        compra.setNumeroNotaFiscal(notaFiscal);
        compra.setValorTotal(new BigDecimal(valor));
        compra.setFormaPagamento(formaPagamento);
        compra.setPosto(posto);
        compra.setStatus(status);
        return compra;
    }

    private Venda venda(String valorBruto, String valorDesconto, FormaPagamento formaPagamento, Posto posto, Status status) {
        Venda venda = new Venda();
        venda.setDataHora(LocalDateTime.now());
        venda.setValorBruto(new BigDecimal(valorBruto));
        venda.setValorDesconto(new BigDecimal(valorDesconto));
        venda.setValorLiquido(new BigDecimal(valorBruto).subtract(new BigDecimal(valorDesconto)));
        venda.setFormaPagamento(formaPagamento);
        venda.setPosto(posto);
        venda.setStatus(status);
        return venda;
    }

    private Promocao promocao(String titulo, String descricao, TipoDesconto tipo, String valor,
                             List<Produto> produtos, List<Servico> servicos) {
        Promocao promocao = new Promocao();
        promocao.setTitulo(titulo);
        promocao.setDescricao(descricao);
        promocao.setTipoDesconto(tipo);
        promocao.setValorDesconto(new BigDecimal(valor));
        promocao.setDataInicio(LocalDate.now());
        promocao.setDataFim(LocalDate.now().plusDays(30));
        promocao.setProdutos(produtos);
        promocao.setServicos(servicos);
        promocao.setAtivo(true);
        return promocao;
    }

    private RegistroPrecoProduto registroPrecoProduto(Produto produto, String valor) {
        RegistroPrecoProduto registro = new RegistroPrecoProduto();
        registro.setProduto(produto);
        registro.setValor(new BigDecimal(valor));
        registro.setDataInicioVigencia(LocalDate.now());
        registro.setDataFimVigencia(LocalDate.now().plusMonths(1));
        registro.setAtivo(true);
        return registro;
    }

    private RegistroPrecoServico registroPrecoServico(Servico servico, String valor) {
        RegistroPrecoServico registro = new RegistroPrecoServico();
        registro.setServico(servico);
        registro.setValor(new BigDecimal(valor));
        registro.setDataInicioVigencia(LocalDate.now());
        registro.setDataFimVigencia(LocalDate.now().plusMonths(1));
        registro.setAtivo(true);
        return registro;
    }

    private void funcionario(Funcionario funcionario, String nome, String cpf, Posto posto, String matricula, String setor) {
        pessoaFisica(funcionario, nome, cpf, "MG-" + cpf.substring(0, 3), posto, "(32) 97777-0000",
                nome.toLowerCase().replace(" ", ".") + "@postomarco.com");
        funcionario.setMaticula(matricula);
        funcionario.setSalario(new BigDecimal("2500.00"));
        funcionario.setDataAdmissao(LocalDate.now().minusMonths(6));
        funcionario.setSenha("senha1234");
        funcionario.setSetor(setor);
    }

    private void pessoaFisica(PessoaFisica pessoa, String nome, String cpf, String rg, Posto posto, String telefone, String email) {
        pessoa(pessoa, nome, posto, telefone, email, "Rua das Flores", "50", "Centro", "Juiz de Fora", "MG", "36000-000");
        pessoa.setCpf(cpf);
        pessoa.setRg(rg);
        pessoa.setDataNascimento(LocalDate.of(1990, 1, 15));
    }

    private void pessoaJuridica(PessoaJuridica pessoa, String nome, String cnpj, String razaoSocial, String nomeFantasia,
                                String inscricaoEstadual, Posto posto, String telefone, String email, String logradouro,
                                String numero, String bairro, String cidade, String estado, String cep) {
        pessoa(pessoa, nome, posto, telefone, email, logradouro, numero, bairro, cidade, estado, cep);
        pessoa.setCnpj(cnpj);
        pessoa.setRazaoSocial(razaoSocial);
        pessoa.setNomeFantasia(nomeFantasia);
        pessoa.setInscricaoEstadual(inscricaoEstadual);
    }

    private void pessoa(Pessoa pessoa, String nome, Posto posto, String telefone, String email, String logradouro,
                        String numero, String bairro, String cidade, String estado, String cep) {
        pessoa.setNome(nome);
        pessoa.setDataCadastro(LocalDate.now());
        pessoa.setPosto(posto);
        pessoa.setAtivo(true);
        pessoa.setTelefone(telefone);
        pessoa.setEmail(email);
        pessoa.setLogradouro(logradouro);
        pessoa.setNumero(numero);
        pessoa.setBairro(bairro);
        pessoa.setCidade(cidade);
        pessoa.setEstado(estado);
        pessoa.setCep(cep);
    }

    private void vendivel(Vendivel vendivel, String nome, Posto posto, String unidade, String preco, String descricao) {
        vendivel.setNome(nome);
        vendivel.setPosto(posto);
        vendivel.setUnidade(unidade);
        vendivel.setPreco(new BigDecimal(preco));
        vendivel.setDescricao(descricao);
        vendivel.setAtivo(true);
    }
}
