package br.com.confeitaria.service;

import br.com.confeitaria.domain.Cliente;
import br.com.confeitaria.domain.ItemOrcamento;
import br.com.confeitaria.domain.Orcamento;
import br.com.confeitaria.domain.StatusOrcamento;
import br.com.confeitaria.infra.OrcamentoRepository;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

public class OrcamentoService {

    private final OrcamentoRepository repository;
    private final CalculadoraOrcamento calculadora;
    private final Clock relogio;

    public OrcamentoService(OrcamentoRepository repository,
                            CalculadoraOrcamento calculadora,
                            Clock relogio) {
        this.repository = repository;
        this.calculadora = calculadora;
        this.relogio = relogio;
    }

   
/** US03 — valida os dados, calcula os valores (RN01–RN03) e salva o orçamento como PENDENTE. */
public Orcamento criar(Cliente cliente, List<ItemOrcamento> itens,
                       LocalDate dataEntrega, BigDecimal distanciaKm) {

    if (cliente == null || cliente.getNome() == null
            || cliente.getNome().isBlank()) {
        throw new IllegalArgumentException("Nome do cliente é obrigatório");
    }

    if (cliente.getTelefone() == null || cliente.getTelefone().isBlank()) {
        throw new IllegalArgumentException("Telefone do cliente é obrigatório");
    }

    if (itens == null || itens.isEmpty()) {
        throw new IllegalArgumentException(
                "O orçamento precisa de pelo menos um item");
    }

    for (ItemOrcamento item : itens) {
        if (item == null || item.getQuantidade() <= 0) {
            throw new IllegalArgumentException(
                    "Quantidade deve ser maior que zero");
        }
    }

    if (dataEntrega == null) {
        throw new IllegalArgumentException("Data de entrega é obrigatória");
    }

    LocalDate hoje = LocalDate.now(relogio);

    Orcamento orcamento = new Orcamento(
            cliente, itens, hoje, dataEntrega, distanciaKm);

    calculadora.validarAntecedencia(
            hoje, dataEntrega, orcamento.temBoloPersonalizado());

    BigDecimal subtotal = calculadora.calcularSubtotal(itens);
    BigDecimal desconto = calculadora.calcularDesconto(subtotal);
    BigDecimal taxaEntrega = calculadora.calcularTaxaEntrega(distanciaKm);

    orcamento.aplicarValores(subtotal, desconto, taxaEntrega);

    return repository.salvar(orcamento);
}


/** US05 — todos os orçamentos, da data de entrega mais próxima para a mais distante. */
public List<Orcamento> listar() {
    return repository.listarTodos().stream()
            .sorted(Comparator.comparing(Orcamento::getDataEntrega))
            .toList();
}

/** US05 — somente os orçamentos com o status informado. */
public List<Orcamento> listarPorStatus(StatusOrcamento status) {
    return listar().stream()
            .filter(o -> o.getStatus() == status)
            .toList();
}
}