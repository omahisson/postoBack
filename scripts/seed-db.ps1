$ErrorActionPreference = "Stop"

$composeFile = Join-Path $PSScriptRoot "..\compose.yaml"
$sqlFile = Join-Path $PSScriptRoot "seed-fake-data.sql"

docker compose -f $composeFile up -d postgres

Write-Host "Aguardando o Postgres ficar pronto..."
$tentativas = 0
do {
    Start-Sleep -Seconds 1
    $status = docker compose -f $composeFile ps postgres --format "{{.State}}"
    if ($status -ne "running") {
        Write-Host "O container do Postgres nao ficou rodando. Logs:"
        docker compose -f $composeFile logs --tail=80 postgres
        exit 1
    }

    docker compose -f $composeFile exec -T postgres pg_isready -U myuser -d testdata 2>$null | Out-Null
    $ready = $LASTEXITCODE -eq 0
    $tentativas++
    if ($tentativas -gt 60) {
        Write-Host "O Postgres nao ficou pronto em 60 segundos. Logs:"
        docker compose -f $composeFile logs --tail=80 postgres
        exit 1
    }
} until ($ready)

Get-Content -Raw -Encoding UTF8 $sqlFile | docker compose -f $composeFile exec -T postgres psql -U myuser -d testdata

docker compose -f $composeFile exec -T postgres psql -U myuser -d testdata -c "
select 'postos' as tabela, count(*) from posto
union all select 'bombas', count(*) from bomba
union all select 'clientes', count(*) from cliente
union all select 'fornecedores', count(*) from fornecedor
union all select 'funcionarios', count(*) from funcionario
union all select 'produtos', count(*) from produto where dtype = 'Produto'
union all select 'combustiveis', count(*) from produto where dtype = 'Combustivel'
union all select 'servicos', count(*) from servico
union all select 'turnos', count(*) from turno
union all select 'compras', count(*) from compra
union all select 'vendas', count(*) from venda
union all select 'promocoes', count(*) from promocao
union all select 'precos_produto', count(*) from registro_preco_produto
union all select 'precos_servico', count(*) from registro_preco_servico
order by tabela;"
