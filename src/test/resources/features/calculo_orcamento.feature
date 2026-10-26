
# language: pt

Funcionalidade: Cálculo do valor do orçamento
  Como atendente da confeitaria
  Quero que o valor do orçamento seja calculado automaticamente
  Para não errar nas contas

  Cenário: Pedido a partir de R$ 300 recebe 5% de desconto
    Dado um pedido de 4 unidades de "Bolo de chocolate" custando "80,00" cada
    Quando calculo o orçamento para retirada no balcão
    Então o desconto é "16,00"
    E o total é "304,00"

  Cenário: Entrega a 10 km cobra taxa de R$ 20
    Dado um pedido de 2 unidades de "Torta de limão" custando "50,00" cada
    Quando calculo o orçamento para entrega a 10 km
    Então a taxa de entrega é "20,00"
    E o total é "120,00"

  Cenário: Bolo personalizado sem antecedência suficiente é recusado
    Dado um pedido com um bolo personalizado
    Quando a entrega é marcada para daqui a 3 dias
    Então o sistema recusa com a mensagem "Entrega exige no mínimo 5 dias de antecedência"
