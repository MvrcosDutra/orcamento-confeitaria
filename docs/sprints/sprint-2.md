# Sprint 2 — Incremento

**Objetivo da sprint:** deixar o código do sistema completo e funcionando: aprovar e recusar
orçamentos (com expiração automática dos pendentes antigos), editar, excluir e buscar produtos e
exigir login do atendente; refinar a tela de orçamento; e entregar o Relatório do Projeto e o Pitch
no Ânima HUB.

Recado da professora (12/11): o código precisa estar pronto e funcionando nesta sprint; os testes
(plano, roteiros executados e usabilidade) ficam para a Sprint 3. Por isso a US09 voltou para esta
sprint e a documentação de testes foi para a próxima.

## Itens da sprint

| Item | Responsável | Issue | Status |
|---|---|---|---|
| Fechamento da Sprint 1 e planejamento da Sprint 2 | Marcos Gabriel Dutra | — | |
| Base: novas opções do menu (5 a 8) e telas vazias | Marcos Gabriel Dutra | — | |
| US08 — Aprovar ou recusar orçamento (RN04) e expiração automática (RN05) | Lavínia Oliveira Silva | #8 | |
| Cenários BDD de status do orçamento | Lavínia Oliveira Silva | #8 | |
| Refinamento: valores com vírgula e datas dd/mm/aaaa na tela de orçamento | Lavínia Oliveira Silva | — | |
| US06 — Editar produto | Frederico Carvalho Truocchio | #6 | |
| US07 — Excluir produto | Frederico Carvalho Truocchio | #7 | |
| US09 — Buscar produto | Frederico Carvalho Truocchio | #9 | |
| US10 — Autenticar atendente | Isabella Castro Garcia | #10 | |
| Verificação final: todas as opções do menu funcionando | Marcos Gabriel Dutra | — | |
| Relatório do Projeto e Pitch (HUB) | Marcos Gabriel Dutra | — | |

## Ordem de trabalho

1. A base entra no `main` primeiro.
2. Depois dela, US06/US07/US09, US08 e US10 são independentes e podem entrar em qualquer ordem.
3. Com tudo no `main`, a verificação final passa por todas as opções do menu antes da entrega.

## Definição de pronto (DoD)

Compila, `mvn test` passa, tem teste das regras implementadas, a issue é fechada pelo PR e **o PR
só é mesclado depois de aprovado por outro integrante** (ação da retrospectiva da Sprint 1).

## Checklist de fechamento

- [ ] Issues #6, #7, #8, #9 e #10 fechadas por PR, cada PR aprovado por outra pessoa
- [ ] `mvn test` passando no `main`
- [ ] Cobertura do pacote `service` ≥ 80%
- [ ] 8 cenários BDD passando
- [ ] Verificação final feita: todas as opções do menu (0 a 8), com entradas certas e erradas
- [ ] README atualizado
- [ ] Relatório do Projeto e Pitch enviados no HUB

## Revisão da sprint

- **Entregue:** _preencher ao final_
- **Não entregue / motivo:** _preencher ao final_

## Retrospectiva

- **O que funcionou:** _preencher_
- **O que melhorar:** _preencher_
- **Ação para a Sprint 3:** _preencher_
