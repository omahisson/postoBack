# Modelagem de Classes do Sistema

Este documento consolida a descricao de atributos para todas as classes encontradas na modelagem anexada em `PostoV4.mdj`, levando em consideracao principios de heranca e polimorfismo para evitar repeticao de dados e manter o dominio coeso.

## Premissas de Modelagem

- A modelagem anexada possui duas ocorrencias da classe `Funcionario`; para o backend, o ideal e unificar em uma unica classe base.
- Nem todas as classes do diagrama possuem atributos explicitados no arquivo `.mdj`, entao os atributos abaixo sao uma proposta de implementacao coerente com o dominio.
- Foram introduzidas classes abstratas auxiliares, como `Pessoa`, `PessoaFisica`, `PessoaJuridica` e `Vendivel`, para aplicar heranca de forma limpa.
- O polimorfismo deve ser usado principalmente em `Pessoa`, `Vendivel` e nas especializacoes de `Funcionario`.

## Hierarquia Proposta

```text
Pessoa
├── PessoaFisica
│   ├── Cliente
│   └── Funcionario
│       ├── Administrador
│       └── Gerente
└── PessoaJuridica
    └── Fornecedor

Vendivel
├── Produto
│   └── Combustivel
└── Servico

Venda
├── VendaCombustivel
└── VendaProduto
```

## Polimorfismo no Dominio

### Pessoas

`Cliente`, `Funcionario`, `Administrador`, `Gerente` e `Fornecedor` compartilham comportamento de cadastro, contato e validacao, mas cada tipo especializa as regras de negocio.

### Itens vendaveis

`Vendivel` representa qualquer item que pode ser comercializado. `Produto`, `Combustivel` e `Servico` podem ser tratados de forma polimorfica em estoque, preco, promocao e venda.

### Funcionarios

`Administrador` e `Gerente` compartilham a base de `Funcionario`, mas podem sobrescrever regras de permissao, autorizacao e visibilidade de operacoes.

## Classes Estruturais de Heranca

### Pessoa

Classe abstrata para centralizar os dados cadastrais comuns a pessoas fisicas e juridicas.

- `id`: identificador unico da entidade.
- `nome`: nome principal usado na exibicao e pesquisa.
- `telefone`: telefone principal para contato.
- `email`: endereco eletronico principal.
- `logradouro`: rua, avenida ou endereco principal.
- `numero`: numero do endereco.
- `bairro`: bairro do endereco.
- `cidade`: cidade do cadastro.
- `estado`: unidade federativa.
- `cep`: codigo postal.
- `dataCadastro`: data em que o registro foi criado.
- `ativo`: indica se o cadastro esta habilitado para uso.

### PessoaFisica

Especializa `Pessoa` para individuos.

- `cpf`: documento fiscal unico da pessoa fisica.
- `dataNascimento`: data de nascimento.
- `rg`: documento de identificacao civil.

### PessoaJuridica

Especializa `Pessoa` para empresas e fornecedores.

- `cnpj`: documento fiscal da pessoa juridica.
- `razaoSocial`: nome juridico da empresa.
- `nomeFantasia`: nome comercial usado no dia a dia.
- `inscricaoEstadual`: registro estadual, quando aplicavel.

## Classes de Pessoas do Sistema

### Funcionario

Classe base para os colaboradores do posto.

- `matricula`: codigo interno do funcionario.
- `salario`: remuneracao base.
- `dataAdmissao`: data de entrada na empresa.
- `cargo`: descricao funcional do colaborador.
- `senha`: credencial de acesso ao sistema, se houver autenticacao local.

### Administrador

Especializacao de `Funcionario` para operacoes administrativas.

- `nivelAcesso`: nivel de privilegio administrativo.

Observacao: se o sistema trabalhar apenas com perfil por papel, esta classe pode nao adicionar novos atributos e usar apenas regras especificas.

### Gerente

Especializacao de `Funcionario` para gestao operacional.

- `setorResponsavel`: area ou unidade sob responsabilidade.
- `bonusMeta`: valor ou percentual de bonificacao por desempenho.

### Cliente

Especializacao de `PessoaFisica` para consumidores.

- `limiteCredito`: limite de compra a prazo.
- `pontosFidelidade`: pontos acumulados em programas de fidelidade.
- `observacoes`: anotacoes relevantes sobre preferencia ou restricao comercial.

### Fornecedor

Especializacao de `PessoaJuridica` para parceiros de fornecimento.

- `contatoResponsavel`: nome do contato principal.
- `prazoEntregaDias`: prazo medio de entrega em dias.
- `categoriaFornecimento`: categoria principal dos itens fornecidos.

## Classes Operacionais do Negocio

### Posto

Classe central do dominio, responsavel por agregar operacoes, estrutura e equipe.

- `id`: identificador do posto.
- `nomeFantasia`: nome comercial da unidade.
- `cnpj`: documento fiscal do posto.
- `telefone`: telefone principal.
- `email`: email institucional.
- `logradouro`: endereco principal.
- `numero`: numero do endereco.
- `bairro`: bairro da unidade.
- `cidade`: cidade da unidade.
- `estado`: estado da unidade.
- `cep`: cep da unidade.
- `dataAbertura`: data de inicio das operacoes.
- `statusOperacao`: situacao atual do posto, como ativo ou inativo.

### Bomba

Representa uma bomba de abastecimento vinculada ao posto.

- `id`: identificador da bomba.
- `codigo`: codigo interno da bomba.
- `numeroSerie`: identificacao de fabrica.
- `tipoBico`: tipo de bico ou saida de abastecimento.
- `dataInstalacao`: data de instalacao do equipamento.
- `ativo`: indica se a bomba pode operar.

### Turno

Representa a jornada ou escala operacional.

- `id`: identificador do turno.
- `nome`: nome do turno, como manha, tarde ou noite.
- `horaInicio`: horario de inicio.
- `horaFim`: horario de encerramento.
- `ativo`: indica se o turno esta em uso.

### Sistema

Representa o sistema computacional ou contexto global de operacao.

- `id`: identificador logico do sistema.
- `nome`: nome do sistema.
- `versao`: versao atual instalada.
- `ambiente`: ambiente de execucao, como desenvolvimento ou producao.
- `dataInicializacao`: data ou hora da ultima inicializacao relevante.

### Log

Registra eventos do sistema e auditoria.

- `id`: identificador do log.
- `nivel`: severidade do evento, como info, warn ou error.
- `mensagem`: descricao do evento.
- `origem`: modulo, classe ou funcionalidade responsavel pelo registro.
- `dataHora`: momento do evento.
- `usuarioResponsavel`: usuario ligado ao evento, quando existir.

## Classes de Itens Comercializados

### Vendivel

Classe abstrata para qualquer item que pode ser vendido.

- `id`: identificador unico do item.
- `nome`: nome comercial do item.
- `descricao`: descricao resumida.
- `unidadeMedida`: unidade usada na venda, como litro, unidade ou servico.
- `ativo`: indica se o item esta disponivel.

### Produto

Especializa `Vendivel` para itens fisicos.

- `codigoBarras`: identificador comercial do produto.
- `marca`: fabricante ou marca principal.
- `categoria`: classificacao do produto.

### Combustivel

Especializa `Produto` para combustiveis.

- `tipoCombustivel`: classificacao do combustivel, como gasolina, etanol ou diesel.
- `octanagem`: indice tecnico, quando aplicavel.
- `composicao`: observacao sobre mistura ou formulacao.

### Servico

Especializa `Vendivel` para servicos prestados no posto.

- `duracaoEstimadaMinutos`: tempo medio de execucao.
- `requerAgendamento`: indica se depende de marcacao previa.
- `descricaoTecnica`: detalhamento do servico executado.

## Classes Comerciais e Financeiras

### RegistroPreco

Armazena o preco vigente de um item vendivel.

- `id`: identificador do registro.
- `valor`: preco monetario praticado.
- `dataInicioVigencia`: inicio da validade do preco.
- `dataFimVigencia`: fim da validade do preco, se existir.
- `ativo`: indica se o registro esta vigente.

### Promocao

Representa campanhas promocionais aplicadas a itens ou servicos.

- `id`: identificador da promocao.
- `titulo`: nome da campanha.
- `descricao`: resumo da promocao.
- `tipoDesconto`: define se o desconto e percentual ou valor fixo.
- `valorDesconto`: percentual ou valor aplicado.
- `dataInicio`: inicio da promocao.
- `dataFim`: encerramento da promocao.
- `ativa`: indica se a promocao esta habilitada.

### Estoque

Controla a disponibilidade de itens vendiveis.

- `id`: identificador do registro de estoque.
- `quantidadeAtual`: saldo disponivel.
- `quantidadeMinima`: nivel minimo aceitavel.
- `quantidadeMaxima`: capacidade maxima planejada.
- `localArmazenamento`: referencia ao tanque, deposito ou area fisica.
- `ultimaAtualizacao`: data da ultima movimentacao.

### Compra

Representa a aquisicao de itens junto a fornecedores.

- `id`: identificador da compra.
- `dataCompra`: data da operacao.
- `valorTotal`: total financeiro da compra.
- `numeroNotaFiscal`: numero da nota fiscal associada.
- `formaPagamento`: forma de pagamento acordada.
- `status`: situacao da compra, como pendente, paga ou cancelada.

### Venda

Classe base para operacoes de venda.

- `id`: identificador da venda.
- `dataHora`: momento da operacao.
- `valorBruto`: valor total antes de descontos.
- `valorDesconto`: desconto concedido.
- `valorLiquido`: valor final cobrado.
- `formaPagamento`: forma de pagamento utilizada.
- `status`: situacao da venda, como concluida ou cancelada.

### VendaCombustivel

Especializacao de `Venda` para abastecimentos.

- `litrosAbastecidos`: volume vendido em litros.
- `valorPorLitro`: preco unitario aplicado.
- `quilometragem`: quilometragem informada no momento do abastecimento, se coletada.
- `placaVeiculo`: placa do veiculo atendido, se houver cadastro.

### VendaProduto

Especializacao de `Venda` para produtos de conveniencia ou loja.

- `quantidadeItens`: quantidade total de itens vendidos.
- `valorMedioItem`: valor medio por item na operacao.
- `cupomFiscal`: numero ou codigo do comprovante fiscal.

## Resumo por Classe

### Classes auxiliares para heranca

- `Pessoa`
- `PessoaFisica`
- `PessoaJuridica`
- `Vendivel`

### Classes de pessoas

- `Funcionario`
- `Administrador`
- `Gerente`
- `Cliente`
- `Fornecedor`

### Classes operacionais

- `Posto`
- `Bomba`
- `Turno`
- `Sistema`
- `Log`

### Classes comerciais

- `Produto`
- `Combustivel`
- `Servico`
- `RegistroPreco`
- `Promocao`
- `Estoque`
- `Compra`
- `Venda`
- `VendaCombustivel`
- `VendaProduto`

## Recomendacao para Implementacao no Backend

Uma sequencia pratica para implementacao seria:

1. criar as classes abstratas `Pessoa`, `PessoaFisica`, `PessoaJuridica` e `Vendivel`
2. criar as classes concretas de pessoas: `Funcionario`, `Cliente` e `Fornecedor`
3. criar as especializacoes `Administrador` e `Gerente`
4. criar `Posto`, `Bomba`, `Turno`, `Sistema` e `Log`
5. criar `Produto`, `Combustivel`, `Servico`, `RegistroPreco`, `Promocao` e `Estoque`
6. criar `Compra`, `Venda`, `VendaCombustivel` e `VendaProduto`
7. ajustar os relacionamentos e cardinalidades conforme a modelagem UML

## Conclusao

Com essa organizacao, a modelagem fica alinhada aos principios de orientacao a objetos:

- heranca para reaproveitamento de atributos comuns
- polimorfismo para tratar entidades do mesmo grupo por uma interface comum
- separacao clara entre pessoas, operacao, estoque e comercializacao

Essa estrutura deixa o `README.md` como referencia funcional para a implementacao do backend.

