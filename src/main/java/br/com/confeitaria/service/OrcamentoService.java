
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

    /** US03 — valida os dados, calcula os valores e salva o orçamento. */
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

    /** US05 — lista todos os orçamentos. */
    public List<Orcamento> listar() {
        atualizarExpirados();
        return repository.listarTodos().stream()
                .sorted(Comparator.comparing(Orcamento::getDataEntrega))
                .toList();
    }

    /** US05 — lista os orçamentos pelo status informado. */
    public List<Orcamento> listarPorStatus(StatusOrcamento status) {
        return listar().stream()
                .filter(o -> o.getStatus() == status)
                .toList();
    }

    /** US08 — aprova um orçamento pendente (RN04). */
    public Orcamento aprovar(Long id) {
        return mudarStatus(id, StatusOrcamento.APROVADO);
    }

    /** US08 — recusa um orçamento pendente (RN04). */
    public Orcamento recusar(Long id) {
        return mudarStatus(id, StatusOrcamento.RECUSADO);
    }

    /** RN05 — expira orçamentos pendentes há mais de 7 dias. */
    public int atualizarExpirados() {
        LocalDate hoje = LocalDate.now(relogio);
        int expirados = 0;

        for (Orcamento orcamento : repository.listarTodos()) {
            if (orcamento.estaVencido(hoje)) {
                orcamento.alterarStatus(StatusOrcamento.EXPIRADO);
                expirados++;
            }
        }

        return expirados;
    }

    private Orcamento mudarStatus(Long id, StatusOrcamento novoStatus) {
        atualizarExpirados();

        Orcamento orcamento = repository.buscarPorId(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Orçamento não encontrado"));

        orcamento.alterarStatus(novoStatus);
        return orcamento;
    }
}
