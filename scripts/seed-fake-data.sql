insert into posto (
    ativo, data_abertura, data_cadastro, posto_id, bairro, cep, cidade, cnpj,
    email, estado, inscricao_estadual, logradouro, nome, nome_fantasia, numero,
    razao_social, telefone
)
select true, current_date - interval '3 years', current_date, null, 'Centro', '36010-000',
       'Juiz de Fora', '11.111.111/0001-11', 'centro@postomarco.com', 'MG',
       'MG-000111', 'Rua das Palmeiras', 'Posto Marco Centro', 'Marco Centro',
       '100', 'Posto Marco Centro LTDA', '(32) 3333-1000'
where not exists (select 1 from posto where cnpj = '11.111.111/0001-11');

insert into posto (
    ativo, data_abertura, data_cadastro, posto_id, bairro, cep, cidade, cnpj,
    email, estado, inscricao_estadual, logradouro, nome, nome_fantasia, numero,
    razao_social, telefone
)
select true, current_date - interval '2 years', current_date, null, 'Industrial', '36080-000',
       'Juiz de Fora', '22.222.222/0001-22', 'norte@postomarco.com', 'MG',
       'MG-000222', 'Avenida Brasil', 'Posto Marco Norte', 'Marco Norte',
       '2200', 'Posto Marco Norte LTDA', '(32) 3333-2000'
where not exists (select 1 from posto where cnpj = '22.222.222/0001-22');

update posto set posto_id = id where posto_id is null;

insert into bomba (ativo, posto_id, codigo, numero_serie)
select true, p.id, 'BOMBA-01', 'NS-BOMBA-0001'
from posto p
where p.cnpj = '11.111.111/0001-11'
  and not exists (select 1 from bomba where codigo = 'BOMBA-01');

insert into bomba (ativo, posto_id, codigo, numero_serie)
select true, p.id, 'BOMBA-02', 'NS-BOMBA-0002'
from posto p
where p.cnpj = '22.222.222/0001-22'
  and not exists (select 1 from bomba where codigo = 'BOMBA-02');

insert into cliente (
    ativo, data_cadastro, data_nascimento, limite_credito, pontos_fidelidade,
    posto_id, bairro, cep, cidade, cpf, email, estado, logradouro, nome,
    numero, observacoes, rg, telefone
)
select true, current_date, date '1992-04-10', 500.00, 120, p.id, 'Centro',
       '36000-000', 'Juiz de Fora', '111.111.111-11', 'ana.souza@email.com',
       'MG', 'Rua das Flores', 'Ana Souza', '50', 'Cliente fake', 'MG-11111111',
       '(32) 98888-0001'
from posto p
where p.cnpj = '11.111.111/0001-11'
  and not exists (select 1 from cliente where cpf = '111.111.111-11');

insert into cliente (
    ativo, data_cadastro, data_nascimento, limite_credito, pontos_fidelidade,
    posto_id, bairro, cep, cidade, cpf, email, estado, logradouro, nome,
    numero, observacoes, rg, telefone
)
select true, current_date, date '1994-08-22', 350.00, 80, p.id, 'Industrial',
       '36080-000', 'Juiz de Fora', '222.222.222-22', 'bruno.lima@email.com',
       'MG', 'Rua das Acacias', 'Bruno Lima', '80', 'Cliente fake', 'MG-22222222',
       '(32) 98888-0002'
from posto p
where p.cnpj = '22.222.222/0001-22'
  and not exists (select 1 from cliente where cpf = '222.222.222-22');

insert into fornecedor (
    ativo, data_cadastro, prazo_entrega_dias, posto_id, bairro,
    categoria_fornecimento, cep, cidade, cnpj, contato_responsavel, email,
    estado, inscricao_estadual, logradouro, nome, nome_fantasia, numero,
    razao_social, telefone
)
select true, current_date, 3, p.id, 'Distrito', 'Combustiveis', '36000-100',
       'Juiz de Fora', '33.333.333/0001-33', 'Marina Costa',
       'contato@alfa.com', 'MG', 'IE-33', 'Rua dos Fornecedores',
       'Distribuidora Alfa', 'Alfa Combustiveis', '45',
       'Distribuidora Alfa LTDA', '(32) 3777-0001'
from posto p
where p.cnpj = '11.111.111/0001-11'
  and not exists (select 1 from fornecedor where cnpj = '33.333.333/0001-33');

insert into fornecedor (
    ativo, data_cadastro, prazo_entrega_dias, posto_id, bairro,
    categoria_fornecimento, cep, cidade, cnpj, contato_responsavel, email,
    estado, inscricao_estadual, logradouro, nome, nome_fantasia, numero,
    razao_social, telefone
)
select true, current_date, 5, p.id, 'Distrito', 'Conveniência', '36000-200',
       'Juiz de Fora', '44.444.444/0001-44', 'Paulo Nunes',
       'contato@lubrimax.com', 'MG', 'IE-44', 'Rua dos Fornecedores',
       'LubriMax Atacado', 'LubriMax', '55', 'LubriMax Atacado LTDA',
       '(32) 3777-0002'
from posto p
where p.cnpj = '22.222.222/0001-22'
  and not exists (select 1 from fornecedor where cnpj = '44.444.444/0001-44');

insert into funcionario (
    cargo, ativo, bonus_meta, data_admissao, data_cadastro, data_nascimento,
    salario, posto_id, bairro, cep, cidade, cpf, email, estado, logradouro,
    maticula, nome, numero, rg, senha, setor, telefone
)
select 'GERENTE', true, 800.00, current_date - interval '6 months', current_date,
       date '1988-05-12', 3500.00, p.id, 'Centro', '36000-000',
       'Juiz de Fora', '333.333.333-33', 'carla.mendes@postomarco.com', 'MG',
       'Rua das Flores', 'GER-001', 'Carla Mendes', '90', 'MG-33333333',
       'senha1234', 'Administracao', '(32) 97777-0001'
from posto p
where p.cnpj = '11.111.111/0001-11'
  and not exists (select 1 from funcionario where cpf = '333.333.333-33');

insert into funcionario (
    cargo, ativo, bonus_meta, data_admissao, data_cadastro, data_nascimento,
    salario, posto_id, bairro, cep, cidade, cpf, email, estado, logradouro,
    maticula, nome, numero, rg, senha, setor, telefone
)
select 'COLABORADOR', true, 0.00, current_date - interval '4 months', current_date,
       date '1996-11-03', 2500.00, p.id, 'Industrial', '36080-000',
       'Juiz de Fora', '444.444.444-44', 'diego.alves@postomarco.com', 'MG',
       'Rua das Acacias', 'COL-001', 'Diego Alves', '30', 'MG-44444444',
       'senha1234', 'Pista', '(32) 97777-0002'
from posto p
where p.cnpj = '22.222.222/0001-22'
  and not exists (select 1 from funcionario where cpf = '444.444.444-44');

insert into produto (
    dtype, ativo, categoria, codigo_barras, descricao, marca, nome, posto_id, preco, unidade
)
select 'Produto', true, 'Lubrificante', '7891000000011', 'Produto fake',
       'Lubrax', 'Oleo Motor 5W30', p.id, 89.90, 'UN'
from posto p
where p.cnpj = '11.111.111/0001-11'
  and not exists (select 1 from produto where codigo_barras = '7891000000011');

insert into produto (
    dtype, ativo, categoria, codigo_barras, descricao, marca, nome, posto_id, preco, unidade
)
select 'Produto', true, 'Aditivo', '7891000000028', 'Produto fake',
       'RadCool', 'Aditivo Radiador', p.id, 24.90, 'UN'
from posto p
where p.cnpj = '22.222.222/0001-22'
  and not exists (select 1 from produto where codigo_barras = '7891000000028');

insert into produto (
    dtype, ativo, categoria, codigo_barras, composicao, descricao, marca, nome,
    octanagem, tipo_combustivel, posto_id, preco, unidade
)
select 'Combustivel', true, 'Gasolina', '7892000000018', 'Gasolina C',
       'Combustivel fake', 'PetroSul', 'Gasolina Comum', '87', 'Gasolina',
       p.id, 5.79, 'L'
from posto p
where p.cnpj = '11.111.111/0001-11'
  and not exists (select 1 from produto where codigo_barras = '7892000000018');

insert into produto (
    dtype, ativo, categoria, codigo_barras, composicao, descricao, marca, nome,
    octanagem, tipo_combustivel, posto_id, preco, unidade
)
select 'Combustivel', true, 'Etanol', '7892000000025', 'Etanol hidratado',
       'Combustivel fake', 'BioMinas', 'Etanol Hidratado', '0', 'Etanol',
       p.id, 3.89, 'L'
from posto p
where p.cnpj = '22.222.222/0001-22'
  and not exists (select 1 from produto where codigo_barras = '7892000000025');

insert into servico (
    ativo, preco, requer_agendamento, posto_id, descricao, descricao_tecnica,
    duracao_estimada_minutos, nome, unidade
)
select true, 35.00, false, p.id, 'Servico fake', 'Descricao tecnica fake',
       '30', 'Lavagem simples', 'SERVICO'
from posto p
where p.cnpj = '11.111.111/0001-11'
  and not exists (select 1 from servico where nome = 'Lavagem simples');

insert into servico (
    ativo, preco, requer_agendamento, posto_id, descricao, descricao_tecnica,
    duracao_estimada_minutos, nome, unidade
)
select true, 90.00, true, p.id, 'Servico fake', 'Descricao tecnica fake',
       '45', 'Alinhamento', 'SERVICO'
from posto p
where p.cnpj = '22.222.222/0001-22'
  and not exists (select 1 from servico where nome = 'Alinhamento');

insert into turno (ativo, hora_inicio, hora_fim, posto_id, nome)
select true, time '06:00', time '14:00', p.id, 'Manha'
from posto p
where p.cnpj = '11.111.111/0001-11'
  and not exists (select 1 from turno where nome = 'Manha');

insert into turno (ativo, hora_inicio, hora_fim, posto_id, nome)
select true, time '14:00', time '22:00', p.id, 'Tarde'
from posto p
where p.cnpj = '22.222.222/0001-22'
  and not exists (select 1 from turno where nome = 'Tarde');

insert into compra (data_compra, status, valor_total, posto_id, forma_pagamento, nota_fiscal)
select current_date + interval '1 day', 0, 12500.00, p.id, 'PIX', 'NF-1001'
from posto p
where p.cnpj = '11.111.111/0001-11'
  and not exists (select 1 from compra where nota_fiscal = 'NF-1001');

insert into compra (data_compra, status, valor_total, posto_id, forma_pagamento, nota_fiscal)
select current_date + interval '2 day', 1, 8700.00, p.id, 'CARTAO_CREDITO', 'NF-1002'
from posto p
where p.cnpj = '22.222.222/0001-22'
  and not exists (select 1 from compra where nota_fiscal = 'NF-1002');

insert into venda (forma_pagamento, status, valor_bruto, valor_desconto, valor_liquido, data_hora, posto_id)
select 3, 1, 250.00, 10.00, 240.00, now(), p.id
from posto p
where p.cnpj = '11.111.111/0001-11'
  and not exists (select 1 from venda where valor_bruto = 250.00 and valor_liquido = 240.00);

insert into venda (forma_pagamento, status, valor_bruto, valor_desconto, valor_liquido, data_hora, posto_id)
select 0, 1, 180.00, 0.00, 180.00, now(), p.id
from posto p
where p.cnpj = '22.222.222/0001-22'
  and not exists (select 1 from venda where valor_bruto = 180.00 and valor_liquido = 180.00);

insert into promocao (ativo, data_fim, data_inicio, valor_desconto, descricao, tipo_desconto, titulo)
select true, current_date + interval '30 days', current_date, 10.00,
       'Desconto em lubrificantes selecionados', 'PERCENTUAL', 'Semana do oleo'
where not exists (select 1 from promocao where titulo = 'Semana do oleo');

insert into promocao (ativo, data_fim, data_inicio, valor_desconto, descricao, tipo_desconto, titulo)
select true, current_date + interval '30 days', current_date, 20.00,
       'Servico com preco especial', 'VALOR_FIXO', 'Combo revisao'
where not exists (select 1 from promocao where titulo = 'Combo revisao');

insert into registro_preco_produto (ativo, data_inicio_vigencia, data_fim_vigencia, valor, produto_id)
select true, current_date, current_date + interval '30 days', 89.90, p.id
from produto p
where p.codigo_barras = '7891000000011'
  and not exists (select 1 from registro_preco_produto where produto_id = p.id);

insert into registro_preco_produto (ativo, data_inicio_vigencia, data_fim_vigencia, valor, produto_id)
select true, current_date, current_date + interval '30 days', 24.90, p.id
from produto p
where p.codigo_barras = '7891000000028'
  and not exists (select 1 from registro_preco_produto where produto_id = p.id);

insert into registro_preco_servico (ativo, data_inicio_vigencia, data_fim_vigencia, valor, servico_id)
select true, current_date, current_date + interval '30 days', 35.00, s.id
from servico s
where s.nome = 'Lavagem simples'
  and not exists (select 1 from registro_preco_servico where servico_id = s.id);

insert into registro_preco_servico (ativo, data_inicio_vigencia, data_fim_vigencia, valor, servico_id)
select true, current_date, current_date + interval '30 days', 90.00, s.id
from servico s
where s.nome = 'Alinhamento'
  and not exists (select 1 from registro_preco_servico where servico_id = s.id);
