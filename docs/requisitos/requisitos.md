# Requisitos

## Atores

- **Atendente**: funcionário ou proprietário da confeitaria que opera o sistema, cadastra
  produtos e monta orçamentos a partir do pedido do cliente.
- **Cliente**: pessoa que solicita o orçamento. Não acessa o sistema diretamente; seus dados
  (nome e telefone) são registrados pelo atendente.

## Requisitos funcionais

| ID | Requisito | Prioridade |
|---|---|---|
| RF01 | O sistema deve permitir cadastrar produtos com nome, categoria (bolo, doce, salgado, adicional) e preço. | Alta |
| RF02 | O sistema deve listar os produtos cadastrados, em ordem alfabética. | Alta |
| RF03 | O sistema deve permitir editar nome, categoria e preço de um produto. | Alta |
| RF04 | O sistema deve permitir excluir um produto. | Média |
| RF05 | O sistema deve permitir buscar produtos por nome ou categoria. | Média |
| RF06 | O sistema deve permitir criar um orçamento informando cliente, itens (produto e quantidade), personalizações, data de entrega e distância de entrega. | Alta |
| RF07 | O sistema deve calcular automaticamente o valor do orçamento aplicando as regras RN01 a RN03. | Alta |
| RF08 | O sistema deve listar os orçamentos, com filtro por status. | Alta |
| RF09 | O sistema deve permitir atualizar o status de um orçamento respeitando a regra RN04. | Alta |
| RF10 | O sistema deve exigir autenticação do atendente (usuário e senha) para acessar as funções. | Média |
| RF11 | O sistema pode sugerir quantidades de produtos a partir do número de convidados de uma festa. | Baixa (opcional) |

## Regras de negócio

| ID | Regra |
|---|---|
| RN00 | Produto deve ter nome não vazio, único (sem diferenciar maiúsculas/minúsculas) e preço maior que zero. |
| RN01 | **Desconto progressivo** sobre o subtotal dos itens: subtotal a partir de R$ 300,00 recebe 5% de desconto; a partir de R$ 600,00 recebe 10%. |
| RN02 | **Taxa de entrega por distância**: até 5 km = R$ 10,00; acima de 5 km até 15 km = R$ 20,00; acima de 15 km a entrega não é aceita (somente retirada). Retirada no balcão não tem taxa. |
| RN03 | **Antecedência mínima**: a data de entrega deve estar a pelo menos 2 dias da data do orçamento; se houver bolo personalizado, a pelo menos 5 dias. |
| RN04 | **Ciclo de vida do orçamento**: todo orçamento nasce `PENDENTE`. Só pode mudar de `PENDENTE` para `APROVADO`, `RECUSADO` ou `EXPIRADO`. Orçamentos aprovados, recusados ou expirados não podem mais ser alterados. Somente orçamentos `PENDENTE` podem ter itens editados. |
| RN05 | **Validade**: um orçamento `PENDENTE` há mais de 7 dias passa a `EXPIRADO`. |

Valor final do orçamento = subtotal − desconto (RN01) + taxa de entrega (RN02).

## Requisitos não funcionais

| ID | Requisito |
|---|---|
| RNF01 | O sistema deve ser desenvolvido em Java 17 com build Maven. |
| RNF02 | O código deve seguir a arquitetura em 4 camadas: `domain`, `service`, `infra` e `ui`. |
| RNF03 | As classes de regra/serviço devem ter no mínimo 80% de cobertura de testes unitários. |
| RNF04 | Valores monetários devem usar `BigDecimal` com 2 casas decimais e arredondamento `HALF_UP`. |
| RNF05 | Mensagens e interface devem estar em português, com mensagens de erro que indiquem como corrigir o problema. |
| RNF06 | Um orçamento com até 5 itens deve poder ser criado em no máximo 3 minutos por um usuário iniciante (verificado no teste de usabilidade). |
| RNF07 | A senha do atendente não deve ser exibida na tela nem armazenada em texto puro. |
