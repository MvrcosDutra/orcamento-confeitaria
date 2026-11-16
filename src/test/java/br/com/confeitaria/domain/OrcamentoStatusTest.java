package br.com.confeitaria.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Testes da US08 (RN04) e da RN05 no domínio. */
class OrcamentoStatusTest {

    private static final LocalDate HOJE = LocalDate.of(2026, 11, 10);

    private Orcamento criadoHa(int dias) {
        Produto bolo = new Produto("Bolo de chocolate", Categoria.BOLO, new BigDecimal("80"));
        return new Orcamento(new Cliente("Ana", "(31) 99999-0000"),
                List.of(new ItemOrcamento(bolo, 1, "", false)),
                HOJE.minusDays(dias), HOJE.plusDays(5), null);
    }

    @Test
    void orcamentoPendentePodeSerAprovado() {
        Orcamento orcamento = criadoHa(1);

        orcamento.alterarStatus(StatusOrcamento.APROVADO);

        assertEquals(StatusOrcamento.APROVADO, orcamento.getStatus());
    }

    @Test
    void orcamentoPendentePodeSerRecusado() {
        Orcamento orcamento = criadoHa(1);

        orcamento.alterarStatus(StatusOrcamento.RECUSADO);

        assertEquals(StatusOrcamento.RECUSADO, orcamento.getStatus());
    }

    @Test
    void orcamentoAprovadoNaoMudaMaisDeStatus() {
        Orcamento orcamento = criadoHa(1);
        orcamento.alterarStatus(StatusOrcamento.APROVADO);

        IllegalArgumentException erro = assertThrows(IllegalArgumentException.class,
                () -> orcamento.alterarStatus(StatusOrcamento.RECUSADO));

        assertEquals("Somente orçamentos pendentes podem mudar de status (este está APROVADO)",
                erro.getMessage());
    }

    @Test
    void naoPodeVoltarParaPendente() {
        Orcamento orcamento = criadoHa(1);

        assertThrows(IllegalArgumentException.class, () ->
                orcamento.alterarStatus(StatusOrcamento.PENDENTE));
    }

    @Test
    void pendenteCriadoHaExatamente7DiasAindaNaoVenceu() {
        assertFalse(criadoHa(7).estaVencido(HOJE));
    }

    @Test
    void pendenteCriadoHa8DiasEstaVencido() {
        assertTrue(criadoHa(8).estaVencido(HOJE));
    }

    @Test
    void orcamentoAprovadoAntigoNaoVence() {
        Orcamento orcamento = criadoHa(30);
        orcamento.alterarStatus(StatusOrcamento.APROVADO);

        assertFalse(orcamento.estaVencido(HOJE));
    }
}
