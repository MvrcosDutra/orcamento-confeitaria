# Orçamento Confeitaria

Sistema para criação e gerenciamento de orçamentos de uma confeitaria. O atendente cadastra
os produtos (bolos, doces, salgados e adicionais) com seus preços e, ao receber um pedido,
monta o orçamento escolhendo produtos, quantidades e personalizações. O sistema calcula
automaticamente o valor estimado aplicando as regras comerciais da confeitaria (descontos
progressivos, taxa de entrega por distância e prazo mínimo de antecedência) e registra o
orçamento para acompanhamento até ser aprovado, recusado ou expirar.

## Problema e público-alvo

Confeitarias pequenas costumam calcular orçamentos manualmente, consultando preços em
lugares diferentes e refazendo contas a cada alteração do cliente, o que gera demora e erros
de preço. O sistema é voltado ao **atendente/proprietário da confeitaria**, que centraliza
preços e regras em um só lugar e produz orçamentos rápidos, padronizados e rastreáveis.

## Funcionalidades

| Funcionalidade | Sprint | Status |
|---|---|---|
| Cadastrar produto | 0 | ✅ Concluída |
| Listar produtos | 0 | ✅ Concluída |
| Criar orçamento com itens | 1 | Planejada |
| Calcular valor do orçamento (regras de negócio) | 1 | Planejada |
| Listar orçamentos | 1 | Planejada |
| Editar / excluir produto | 1 | Planejada |
| Atualizar status do orçamento | 2 | Planejada |
| Buscar produto por nome/categoria | 2 | Planejada |
| Autenticar atendente | 2 | Planejada |

Detalhes em [docs/requisitos/requisitos.md](docs/requisitos/requisitos.md) e
[docs/backlog/backlog.md](docs/backlog/backlog.md).

## Tecnologias

- Java 17
- Maven
- JUnit 5 (testes unitários)
- Cucumber 7 (testes BDD em Gherkin)
- JaCoCo (cobertura de testes)
- Interface em console (terminal)

## Como executar

Pré-requisitos: JDK 17+ e Maven 3.8+ instalados.

```bash
mvn compile exec:java
```

## Como rodar os testes

```bash
mvn test
```

O relatório de cobertura é gerado em `target/site/jacoco/index.html`.

## Estrutura de pastas

```
src/main/java/br/com/confeitaria/
├── domain/    # entidades e regras do domínio (Produto, Categoria)
├── service/   # casos de uso e validações (ProdutoService)
├── infra/     # persistência (repositórios)
├── ui/        # interface com o usuário (menu de console)
└── App.java   # ponto de entrada
src/test/java/             # testes unitários e steps do Cucumber
src/test/resources/features/  # cenários BDD (Gherkin)
docs/
├── requisitos/   # requisitos funcionais, não funcionais e regras de negócio
├── backlog/      # backlog do produto (histórias de usuário)
├── sprints/      # planejamento e revisão de cada sprint
└── arquitetura.md
```

## Equipe

| Integrante | Papel na Sprint 0 |
|---|---|
| [Nome 1] | Product Owner |
| [Nome 2] | Scrum Master |
| [Nome 3] | Desenvolvedor(a) |
| [Nome 4] | Desenvolvedor(a) |
| [Nome 5] | Desenvolvedor(a) / Testes |
| [Nome 6] | Desenvolvedor(a) / Documentação |

## Gestão do projeto

O backlog e o andamento das sprints estão no quadro do GitHub Projects (colunas **Backlog**,
**Em Progresso**, **Em Revisão** e **Concluído**). Toda entrega é rastreável por issue e pull request.
