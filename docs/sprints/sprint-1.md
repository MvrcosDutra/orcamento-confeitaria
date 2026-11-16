# Sprint 1 — MVP

**Objetivo da sprint:** criar um orçamento com cálculo automático do valor (desconto progressivo,
taxa de entrega e antecedência mínima) e listar os orçamentos criados, com testes unitários e BDD,
e entregar o Pré-Projeto e o Canvas no Ânima HUB.

US06 e US07 (editar e excluir produto) foram movidas para a Sprint 2 para manter o foco no
fluxo principal do orçamento.

## Itens da sprint

| Item | Responsável | Issue | Status |
|---|---|---|---|
| Base: classes do domínio, repositório de orçamentos e telas | Frederico Carvalho Truocchio | #12 | Concluído |
| US04 — Calcular valor do orçamento (RN01, RN02, RN03) | Isabella Castro Garcia | #4 | Concluído |
| Cenários BDD do cálculo | Isabella Castro Garcia | #4 | Concluído |
| US03 — Criar orçamento com itens | Lavínia Oliveira Silva | #3 | Concluído |
| US05 — Listar orçamentos | Frederico Carvalho Truocchio | #5 | Concluído |
| Pré-Projeto (HUB) | Marcos Gabriel Dutra | — | Concluído |
| Canvas (HUB) | Marcos Gabriel Dutra | — | Concluído |
| Revisão dos PRs, quadro e README | Marcos Gabriel Dutra | — | Concluído |

## Ordem de trabalho

1. A base entra no `main` primeiro; as demais histórias partem dela.
2. US04 e US05 são independentes entre si.
3. US03 usa a calculadora da US04: as validações de entrada podem ser feitas antes, e o cálculo é
   integrado depois que a US04 estiver no `main`.

## Definição de pronto (DoD)

Igual à Sprint 0: compila, `mvn test` passa, tem teste das regras implementadas, entrou no `main`
por pull request revisado por outro integrante e a issue foi fechada pelo PR.

## Checklist de fechamento

- [ ] Issues #3, #4 e #5 fechadas por PR, cada PR aprovado por outra pessoa — **parcial:** os PRs #13, #14, #15 e #17 foram mesclados pelo próprio autor
- [x] Todos os integrantes com commits no histórico
- [x] `mvn test` passando no `main` (50 testes)
- [x] Cobertura do pacote `service` ≥ 80% (`target/site/jacoco/index.html`)
- [x] 5 cenários BDD passando
- [x] Fluxo completo testado no menu: cadastrar produto → novo orçamento → listar orçamentos
- [x] README atualizado
- [x] Pré-Projeto e Canvas enviados no HUB

## Revisão da sprint

- **Entregue:** criação de orçamento com cálculo automático (desconto progressivo, taxa de entrega
  por distância e antecedência mínima) e listagem de orçamentos com filtro por status. 50 testes
  passando (45 unitários e 5 cenários BDD) e cobertura acima de 85% nas classes de serviço.
  Pré-Projeto e Canvas enviados no Ânima HUB.
- **Não entregue / motivo:** US06 e US07 foram replanejadas para a Sprint 2 para manter o foco no
  fluxo principal do orçamento.

## Retrospectiva

- **O que funcionou:** entregar primeiro uma base com as classes compartilhadas permitiu que cada
  pessoa trabalhasse em paralelo na sua história, sem conflitos de merge. Os testes de valor-limite
  deram segurança nas regras de preço.
- **O que melhorar:** quatro PRs foram mesclados pelo próprio autor, sem revisão; dois arquivos foram
  para pastas erradas (`Service` com S maiúsculo e um teste fora de `src/test/java`), o que fez 16
  testes deixarem de rodar até a correção; a US03 entrou antes da US04, e o menu travou por um tempo.
- **Ação para a Sprint 2:** todo PR só entra depois de aprovado por outra pessoa, e cada um confere
  o número de testes (`Tests run`) antes de abrir o PR.
