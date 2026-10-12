# language: pt
Funcionalidade: Cadastro de produtos
  Como atendente da confeitaria
  Quero cadastrar produtos com nome, categoria e preço
  Para usá-los na montagem dos orçamentos

  Cenário: Cadastrar produto válido
    Dado que não há produtos cadastrados
    Quando eu cadastro o produto "Bolo de chocolate" da categoria "BOLO" com preço "80,00"
    Então o produto "Bolo de chocolate" aparece na lista de produtos

  Cenário: Rejeitar produto com preço zero
    Dado que não há produtos cadastrados
    Quando eu cadastro o produto "Coxinha" da categoria "SALGADO" com preço "0"
    Então vejo a mensagem de erro "Preço deve ser maior que zero"
    E a lista de produtos está vazia
