# Sprint 1 — MVP

**Objetivo da sprint:** criar um orçamento com cálculo automático do valor (desconto progressivo,
taxa de entrega e antecedência mínima) e listar os orçamentos criados, com testes unitários e BDD,
e entregar o Pré-Projeto e o Canvas no Ânima HUB.

US06 e US07 (editar e excluir produto) foram movidas para a Sprint 2 para manter o foco no
fluxo principal do orçamento.

## Itens da sprint

| Item | Responsável | Issue | Status |
|---|---|---|---|
| Base: classes do domínio, repositório de orçamentos e telas | Frederico Carvalho Truocchio | — | |
| US04 — Calcular valor do orçamento (RN01, RN02, RN03) | Isabella Castro Garcia | #4 | |
| Cenários BDD do cálculo | Isabella Castro Garcia | #4 | |
| US03 — Criar orçamento com itens | Lavínia Oliveira Silva | #3 | |
| US05 — Listar orçamentos | Frederico Carvalho Truocchio | #5 | |
| Pré-Projeto (HUB) | Marcos Gabriel Dutra | — | |
| Canvas (HUB) | Frederico Carvalho Truocchio | — | |
| Revisão dos PRs, quadro e README | Marcos Gabriel Dutra | — | |

## Ordem de trabalho

1. A base entra no `main` primeiro; as demais histórias partem dela.
2. US04 e US05 são independentes entre si.
3. US03 usa a calculadora da US04: as validações de entrada podem ser feitas antes, e o cálculo é
   integrado depois que a US04 estiver no `main`.

## Definição de pronto (DoD)

Igual à Sprint 0: compila, `mvn test` passa, tem teste das regras implementadas, entrou no `main`
por pull request revisado por outro integrante e a issue foi fechada pelo PR.

## Checklist de fechamento

- [ ] Issues #3, #4 e #5 fechadas por PR, cada PR aprovado por outra pessoa
- [ ] Todos os integrantes com commits no histórico
- [ ] `mvn test` passando no `main`
- [ ] Cobertura do pacote `service` ≥ 80% (`target/site/jacoco/index.html`)
- [ ] 5 cenários BDD passando
- [ ] Fluxo completo testado no menu: cadastrar produto → novo orçamento → listar orçamentos
- [ ] README atualizado
- [ ] Pré-Projeto e Canvas enviados no HUB

## Revisão da sprint

- **Entregue:** _preencher ao final_
- **Não entregue / motivo:** _preencher ao final_

## Retrospectiva

- **O que funcionou:** _preencher_
- **O que melhorar:** _preencher_
- **Ação para a Sprint 2:** _preencher_
