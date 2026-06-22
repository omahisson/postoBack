param(
    [string]$BaseUrl = "http://localhost:8081",
    [Parameter(Mandatory = $true)]
    [string]$Token,
    [string]$PayloadInvalido = "{}"
)

$ErrorActionPreference = "Stop"
$headers = @{ Authorization = "Bearer $Token" }
$documento = Invoke-RestMethod -Uri "$BaseUrl/v3/api-docs/POSTOBACK%20-%20API%20v1"
$resultados = @()

function Get-UrlDeTeste([string]$Path, $Operation) {
    $url = $Path -replace "\{[^}]+\}", "999999"
    $queries = @()
    foreach ($parametro in @($Operation.parameters)) {
        if ($parametro.in -eq "query" -and $parametro.required) {
            $valor = if ($parametro.name -eq "idPosto") { "1" } else { "invalido" }
            $queries += "{0}={1}" -f $parametro.name, $valor
        }
    }
    if ($queries.Count -gt 0) {
        $url += "?" + ($queries -join "&")
    }
    return $url
}

foreach ($caminho in $documento.paths.PSObject.Properties) {
    foreach ($operacao in $caminho.Value.PSObject.Properties) {
        $metodo = $operacao.Name.ToUpperInvariant()
        if ($metodo -notin @("GET", "POST", "PUT", "DELETE", "PATCH")) {
            continue
        }

        $url = "$BaseUrl$(Get-UrlDeTeste $caminho.Name $operacao.Value)"
        $parametros = @{
            Uri = $url
            Method = $metodo
            Headers = $headers
            UseBasicParsing = $true
        }
        if ($metodo -in @("POST", "PUT", "PATCH")) {
            $parametros.ContentType = "application/json"
            $parametros.Body = $PayloadInvalido
        }

        try {
            $resposta = Invoke-WebRequest @parametros
            $corpo = $resposta.Content
            $resultados += [PSCustomObject]@{
                Metodo = $metodo
                Rota = $caminho.Name
                Status = [int]$resposta.StatusCode
                Generico = $corpo -match "erro de regra de negocio|mensagem de erro da regra"
                ErroServidor = [int]$resposta.StatusCode -ge 500
                Corpo = $corpo
            }
        } catch {
            $respostaHttp = $_.Exception.Response
            if ($null -ne $respostaHttp) {
                $leitor = New-Object System.IO.StreamReader($respostaHttp.GetResponseStream())
                $corpo = $leitor.ReadToEnd()
                $leitor.Close()
                $resultados += [PSCustomObject]@{
                    Metodo = $metodo
                    Rota = $caminho.Name
                    Status = [int]$respostaHttp.StatusCode
                    Generico = $corpo -match "erro de regra de negocio|mensagem de erro da regra"
                    ErroServidor = [int]$respostaHttp.StatusCode -ge 500
                    Corpo = $corpo
                }
                continue
            }
            $resultados += [PSCustomObject]@{
                Metodo = $metodo
                Rota = $caminho.Name
                Status = 0
                Generico = $false
                ErroServidor = $true
                Corpo = $_.Exception.Message
            }
        }
    }
}

$resultados | Sort-Object Metodo, Rota | Format-Table Metodo, Rota, Status, Generico, ErroServidor -AutoSize
"`nResumo:"
$resultados | Group-Object Status | Sort-Object Name | ForEach-Object { "HTTP $($_.Name): $($_.Count)" }
"Mensagens genericas: $(@($resultados | Where-Object Generico).Count)"
"Respostas 5xx: $(@($resultados | Where-Object ErroServidor).Count)"
