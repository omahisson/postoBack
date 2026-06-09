Abra o programa do Docker.

Pelo PowerShell:

```powershell
cd C:\Users\dev\postoMrMarco\postoBack
```

Atualize esse caminho para o caminho da pasta no seu PC.

```powershell
docker compose down -v
docker compose up -d postgres
& 'C:\Users\dev\.m2\wrapper\dists\apache-maven-3.9.12\59fe215c0ad6947fea90184bf7add084544567b927287592651fda3782e0e798\bin\mvn.cmd' spring-boot:run
```

O que cada um faz:

`docker compose down -v` apaga o banco antigo, inclusive os dados.

`docker compose up -d postgres` cria/sobe o PostgreSQL de novo.

`mvn spring-boot:run` liga o backend; ao iniciar, ele recria as tabelas e coloca os dados fake.
