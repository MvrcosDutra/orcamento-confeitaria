package br.com.confeitaria.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OrcamentoTest {

    private final Produto bolo =
            new Produto("Bolo de chocolate", Categoria.BOLO, new BigDecimal("80"));

    private final Produto brigadeiro =
            new Produto("Brigadeiro", Categoria.DOCE, new BigDecimal("2.50"));

    private final Cliente cliente =
            new Cliente("Ana", "(11) 99999-0000");

    private final LocalDate hoje = LocalDate.of(2026, 11, 2);

    @Test
    void itemDeveCalcularPrecoVezesQuantidade() {
        ItemOrcamento item =
                new ItemOrcamento(brigadeiro, 50, "", false);

        assertEquals(new BigDecimal("125.00"), item.getSubtotal());
    }

    @Test
    void orcamentoNovoDeveFicarPendente() {
        Orcamento orcamento =
                new Orcamento(cliente, List.of(), hoje, hoje.plusDays(3), null);

        assertEquals(StatusOrcamento.PENDENTE, orcamento.getStatus());
        assertTrue(orcamento.isRetirada());
    }

    @Test
    void deveIdentificarBoloPersonalizado() {
        Orcamento comBolo = new Orcamento(
                cliente,
                List.of(new ItemOrcamento(bolo, 1, "Tema princesa", true)),
                hoje,
                hoje.plusDays(5),
                null
        );

        Orcamento docePersonalizado = new Orcamento(
                cliente,
                List.of(new ItemOrcamento(brigadeiro, 50, "Forminha rosa", true)),
                hoje,
                hoje.plusDays(5),
                null
        );

        assertTrue(comBolo.temBoloPersonalizado());
        assertFalse(docePersonalizado.temBoloPersonalizado());
    }

    @Test
    void totalDeveSerSubtotalMenosDescontoMaisTaxa() {
        Orcamento orcamento = new Orcamento(
                cliente,
                List.of(),
                hoje,
                hoje.plusDays(3),
                new BigDecimal("10")
        );

        orcamento.aplicarValores(
                new BigDecimal("320"),
                new BigDecimal("16"),
                new BigDecimal("20")
        );

        assertEquals(new BigDecimal("324.00"), orcamento.getTotal());
    }
}