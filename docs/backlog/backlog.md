# Backlog do produto

Cada história abaixo deve virar uma **issue** no GitHub (use o template "História de usuário")
e um cartão no quadro do projeto. A ordem da tabela é a ordem de prioridade.

| ID | História | Requisitos | Prioridade | Sprint |
|---|---|---|---|---|
| US01 | Cadastrar produto | RF01, RN00 | Must | 0 |
| US02 | Listar produtos | RF02 | Must | 0 |
| US03 | Criar orçamento com itens | RF06, RN03 | Must | 1 |
| US04 | Calcular valor do orçamento | RF07, RN01, RN02 | Must | 1 |
| US05 | Listar orçamentos | RF08 | Must | 1 |
| US06 | Editar produto | RF03, RN00 | Should | 1 |
| US07 | Excluir produto | RF04 | Should | 1 |
| US08 | Atualizar status do orçamento | RF09, RN04, RN05 | Must | 2 |
| US09 | Buscar produto | RF05 | Should | 2 |
| US10 | Autenticar atendente | RF10, RNF07 | Should | 2 |
| US11 | Sugerir quantidades para festas | RF11 | Could | — |

---

## US01 — Cadastrar produto

**Como** atendente, **quero** cadastrar produtos com nome, categoria e preço **para** usá-los
na montagem dos orçamentos.

Critérios de aceite:
- Dado nome, categoria e preço válidos, o produto é salvo e recebe um código.
- Nome vazio é rejeitado com a mensagem "Nome do produto é obrigatório".
- Preço zero ou negativo é rejeitado com a mensagem "Preço deve ser maior que zero".
- Nome já cadastrado (ignorando maiúsculas/minúsculas) é rejeitado.

## US02 — Listar produtos

**Como** atendente, **quero** ver todos os produtos cadastrados **para** consultar os preços.

Critérios de aceite:
- A lista mostra código, nome, categoria e preço, em ordem alfabética.
- Sem produtos, é exibida a mensagem "Nenhum produto cadastrado".

## US03 — Criar orçamento com itens

**Como** atendente, **quero** registrar um orçamento com cliente, itens e data de entrega
**para** guardar o pedido do cliente.

Critérios de aceite:
- O orçamento exige nome e telefone do cliente e ao menos um item com quantidade > 0.
- Data de entrega com menos de 2 dias de antecedência é rejeitada (5 dias se houver bolo personalizado).
- O orçamento é criado com status `PENDENTE` e data de criação.

## US04 — Calcular valor do orçamento

**Como** atendente, **quero** que o valor seja calculado automaticamente **para** não errar nas contas.

Critérios de aceite:
- Subtotal = soma de preço × quantidade de cada item.
- Subtotal ≥ R$ 300 → 5% de desconto; ≥ R$ 600 → 10%.
- Entrega até 5 km → R$ 10; até 15 km → R$ 20; acima de 15 km → entrega recusada; retirada → R$ 0.
- O resumo mostra subtotal, desconto, taxa de entrega e total.

## US05 — Listar orçamentos

**Como** atendente, **quero** listar os orçamentos, filtrando por status, **para** acompanhar os pedidos.

Critérios de aceite:
- A lista mostra código, cliente, data de entrega, total e status.
- É possível filtrar por `PENDENTE`, `APROVADO`, `RECUSADO` ou `EXPIRADO`.

## US06 — Editar produto

**Como** atendente, **quero** alterar os dados de um produto **para** manter os preços atualizados.

Critérios de aceite:
- As mesmas validações do cadastro (RN00) se aplicam.
- Código inexistente exibe "Produto não encontrado".

## US07 — Excluir produto

**Como** atendente, **quero** excluir um produto **para** tirar do cardápio o que não é mais vendido.

Critérios de aceite:
- O sistema pede confirmação antes de excluir.
- Código inexistente exibe "Produto não encontrado".

## US08 — Atualizar status do orçamento

**Como** atendente, **quero** aprovar ou recusar um orçamento **para** controlar quais pedidos serão produzidos.

Critérios de aceite:
- Só orçamentos `PENDENTE` podem mudar de status.
- Tentativa de alterar orçamento finalizado exibe mensagem de erro.
- Orçamentos pendentes há mais de 7 dias aparecem como `EXPIRADO`.

## US09 — Buscar produto

**Como** atendente, **quero** buscar produtos por parte do nome ou por categoria **para** encontrá-los rapidamente.

Critérios de aceite:
- A busca por nome não diferencia maiúsculas/minúsculas e aceita trechos ("choc" encontra "Bolo de chocolate").
- Busca sem resultados exibe "Nenhum produto encontrado".

## US10 — Autenticar atendente

**Como** dono da confeitaria, **quero** que apenas atendentes autenticados usem o sistema **para** proteger os dados.

Critérios de aceite:
- Usuário e senha corretos dão acesso ao menu principal.
- Após 3 tentativas erradas o sistema encerra.
- A senha é armazenada como hash.

## US11 — Sugerir quantidades para festas (opcional)

**Como** atendente, **quero** informar o número de convidados e receber uma sugestão de
quantidade de bolo e doces **para** ajudar o cliente a decidir.
