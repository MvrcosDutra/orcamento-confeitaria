# Arquitetura

Aplicação Java de console organizada em 4 camadas. Cada camada só depende da camada abaixo dela.

```
┌──────────────────────────────┐
│ ui        MenuConsole        │  lê entradas e exibe resultados
├──────────────────────────────┤
│ service   ProdutoService     │  casos de uso e regras de negócio
├──────────────────────────────┤
│ infra     ProdutoRepository  │  persistência (em memória na Sprint 0)
├──────────────────────────────┤
│ domain    Produto, Categoria │  entidades do negócio
└──────────────────────────────┘
```

| Camada | Pacote | Responsabilidade |
|---|---|---|
| domain | `br.com.confeitaria.domain` | Entidades (`Produto`, `Categoria`; depois `Orcamento`, `ItemOrcamento`, `Cliente`, `StatusOrcamento`). Não depende de nenhuma outra camada. |
| service | `br.com.confeitaria.service` | Validações e regras de negócio (RN00–RN05). É onde ficam os testes unitários com cobertura ≥ 80%. |
| infra | `br.com.confeitaria.infra` | Interfaces de repositório e suas implementações. Na Sprint 0 os dados ficam em memória; a implementação pode ser trocada (arquivo, banco) sem alterar o `service`. |
| ui | `br.com.confeitaria.ui` | Menu de console. Não contém regra de negócio: só chama o `service` e mostra o resultado ou a mensagem de erro. |

O `App` monta as dependências (repositório → serviço → menu) e inicia o menu.

## Classes previstas

| Sprint | Classes |
|---|---|
| 0 | `Produto`, `Categoria`, `ProdutoRepository`, `ProdutoRepositoryEmMemoria`, `ProdutoService`, `MenuConsole`, `App` |
| 1 | `Cliente`, `Orcamento`, `ItemOrcamento`, `StatusOrcamento`, `OrcamentoRepository`, `OrcamentoRepositoryEmMemoria`, `CalculadoraOrcamento`, `OrcamentoService` |
| 2 | `Atendente`, `AtendenteRepository`, `AutenticacaoService` |
