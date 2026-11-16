# language: pt
Funcionalidade: Aprovação e expiração de orçamentos
  Como atendente da confeitaria
  Quero aprovar ou recusar os orçamentos pendentes
  Para controlar quais pedidos serão produzidos

  Cenário: Aprovar um orçamento pendente
    Dado um orçamento pendente criado há 1 dia
    Quando o atendente aprova o orçamento
    Então o status do orçamento é "APROVADO"

  Cenário: Orçamento recusado não pode ser aprovado depois
    Dado um orçamento pendente criado há 1 dia
    E o atendente recusa o orçamento
    Quando o atendente aprova o orçamento
    Então a alteração é bloqueada com a mensagem "Somente orçamentos pendentes podem mudar de status (este está RECUSADO)"

  Cenário: Orçamento pendente há mais de 7 dias expira sozinho
    Dado um orçamento pendente criado há 8 dias
    Quando o atendente consulta os orçamentos
    Então o status do orçamento é "EXPIRADO"