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
        throw new UnsupportedOperationException("US03 — em desenvolvimento");
    }

    /** US05 — todos os orçamentos, da data de entrega mais próxima para a mais distante. */
    public List<Orcamento> listar() {
        throw new UnsupportedOperationException("US05 — em desenvolvimento");
    }

    /** US05 — somente os orçamentos com o status informado. */
    public List<Orcamento> listarPorStatus(StatusOrcamento status) {
        throw new UnsupportedOperationException("US05 — em desenvolvimento");
    }
}