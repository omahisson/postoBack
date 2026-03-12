# Modelagem de Classes do Sistema

Este documento consolida a proposta de modelagem para `Funcionario`, `Cliente` e `Fornecedor` com foco em heranca para evitar repeticao de atributos. A proposta foi baseada na modelagem anexada em `PostoV4.mdj`.

## Objetivo

Evitar repeticao de atributos comuns entre pessoas fisicas e juridicas, mantendo uma estrutura simples para implementacao no backend.

## Observacao Importante

Na modelagem anexada aparecem duas ocorrencias da classe `Funcionario`. Antes de implementar o backend, o ideal e unificar isso em uma unica classe base de funcionario.

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
```

## Classes e Atributos

### Pessoa

Classe abstrata para reunir os atributos comuns.

- `id`
- `nome`
- `telefone`
- `email`
- `logradouro`
- `numero`
- `bairro`
- `cidade`
- `estado`
- `cep`

### PessoaFisica

Especializacao de `Pessoa` para entidades que representam individuos.

- `cpf`
- `dataNascimento`

### PessoaJuridica

Especializacao de `Pessoa` para entidades empresariais.

- `cnpj`
- `razaoSocial`
- `nomeFantasia`
- `inscricaoEstadual`

### Funcionario

Herda de `PessoaFisica` e concentra os dados comuns aos colaboradores do sistema.

- `matricula`
- `salario`
- `dataAdmissao`
- `ativo`

### Cliente

Herda de `PessoaFisica`.

- `limiteCredito`

Observacao: se o sistema usar fidelidade, esse atributo pode ser trocado ou complementado por `pontosFidelidade`.

### Fornecedor

Herda de `PessoaJuridica`.

- `contatoResponsavel`
- `prazoEntrega`
- `ativo`

### Administrador

Herda de `Funcionario`.

Sugestao: nao precisa de atributo proprio neste momento, a menos que exista algum controle de permissao especifico.

### Gerente

Herda de `Funcionario`.

Sugestao: tambem pode permanecer sem atributo proprio inicialmente.

## Resumo da Estrutura

### Reaproveitamento de atributos

- `Pessoa` concentra dados cadastrais comuns
- `PessoaFisica` concentra dados pessoais
- `PessoaJuridica` concentra dados empresariais
- `Funcionario`, `Cliente` e `Fornecedor` ficam com apenas os atributos especificos do seu papel

### Beneficios

- reduz duplicacao
- facilita manutencao
- melhora organizacao do dominio
- simplifica a implementacao de entidades no backend

## Exemplo em Java

### Pessoa

```java
public abstract class Pessoa {
    private Long id;
    private String nome;
    private String telefone;
    private String email;
    private String logradouro;
    private String numero;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;
}
```

### PessoaFisica

```java
import java.time.LocalDate;

public abstract class PessoaFisica extends Pessoa {
    private String cpf;
    private LocalDate dataNascimento;
}
```

### PessoaJuridica

```java
public abstract class PessoaJuridica extends Pessoa {
    private String cnpj;
    private String razaoSocial;
    private String nomeFantasia;
    private String inscricaoEstadual;
}
```

### Funcionario

```java
import java.math.BigDecimal;
import java.time.LocalDate;

public class Funcionario extends PessoaFisica {
    private String matricula;
    private BigDecimal salario;
    private LocalDate dataAdmissao;
    private Boolean ativo;
}
```

### Cliente

```java
import java.math.BigDecimal;

public class Cliente extends PessoaFisica {
    private BigDecimal limiteCredito;
}
```

### Fornecedor

```java
public class Fornecedor extends PessoaJuridica {
    private String contatoResponsavel;
    private Integer prazoEntrega;
    private Boolean ativo;
}
```

### Administrador

```java
public class Administrador extends Funcionario {
}
```

### Gerente

```java
public class Gerente extends Funcionario {
}
```

## Recomendacao para o Backend

Se voce for implementar isso com JPA ou Spring Boot, essa estrutura funciona bem para:

- entidades
- DTOs
- validacoes
- regras de negocio por tipo de usuario

Uma boa sequencia de implementacao seria:

1. criar `Pessoa`, `PessoaFisica` e `PessoaJuridica`
2. criar `Funcionario`, `Cliente` e `Fornecedor`
3. criar `Administrador` e `Gerente` herdando de `Funcionario`
4. ajustar os relacionamentos com `Posto`

## Conclusao

A melhor forma de modelar `Funcionario`, `Cliente` e `Fornecedor` sem repetir atributos e separar o dominio em:

- `Pessoa`
- `PessoaFisica`
- `PessoaJuridica`

e depois especializar em:

- `Funcionario`
- `Cliente`
- `Fornecedor`
- `Administrador`
- `Gerente`

Essa abordagem deixa o backend mais limpo, extensivel e coerente com a modelagem orientada a objetos.

