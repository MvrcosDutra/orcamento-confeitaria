package br.com.confeitaria.service;

import br.com.confeitaria.domain.Categoria;
import br.com.confeitaria.domain.Cliente;
import br.com.confeitaria.domain.ItemOrcamento;
import br.com.confeitaria.domain.Orcamento;
import br.com.confeitaria.domain.Produto;
import br.com.confeitaria.domain.StatusOrcamento;
import br.com.confeitaria.infra.OrcamentoRepositoryEmMemoria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/** Testes da US08 e RN05. */
class OrcamentoServiceStatusTest {

    private static final LocalDate HOJE = LocalDate.of(2026, 11, 10);

    private OrcamentoRepositoryEmMemoria repository;
    private OrcamentoService service;

    @BeforeEach
    void setUp() {
        repository = new OrcamentoRepositoryEmMemoria();
        Clock relogio = Clock.fixed(
                Instant.parse("2026-11-10T12:00:00Z"),
                ZoneId.of("America/Sao_Paulo"));
        service = new OrcamentoService(repository, new CalculadoraOrcamento(), relogio);
    }

    private Orcamento salvarCriadoHa(int dias) {
        Produto bolo = new Produto("Bolo de chocolate", Categoria.BOLO, new BigDecimal("80"));
        Orcamento orcamento = new Orcamento(
                new Cliente("Ana", "(31) 99999-0000"),
                List.of(new ItemOrcamento(bolo, 1, "", false)),
                HOJE.minusDays(dias), HOJE.plusDays(5), null);
        return repository.salvar(orcamento);
    }

    @Test
    void deveAprovarPeloCodigo() {
        Orcamento orcamento = salvarCriadoHa(1);

        service.aprovar(orcamento.getId());

        assertEquals(StatusOrcamento.APROVADO, orcamento.getStatus());
    }

    @Test
    void deveRecusarPeloCodigo() {
        Orcamento orcamento = salvarCriadoHa(1);

        service.recusar(orcamento.getId());

        assertEquals(StatusOrcamento.RECUSADO, orcamento.getStatus());
    }

    @Test
    void codigoInexistenteDaErro() {
        IllegalArgumentException erro = assertThrows(
                IllegalArgumentException.class,
                () -> service.aprovar(99L));

        assertEquals("Orçamento não encontrado", erro.getMessage());
    }

    @Test
    void naoAprovaOrcamentoJaRecusado() {
        Orcamento orcamento = salvarCriadoHa(1);
        service.recusar(orcamento.getId());

        assertThrows(IllegalArgumentException.class,
                () -> service.aprovar(orcamento.getId()));
    }

    @Test
    void expiraPendenteCriadoHaMaisDe7Dias() {
        Orcamento antigo = salvarCriadoHa(8);
        Orcamento recente = salvarCriadoHa(7);

        int expirados = service.atualizarExpirados();

        assertEquals(1, expirados);
        assertEquals(StatusOrcamento.EXPIRADO, antigo.getStatus());
        assertEquals(StatusOrcamento.PENDENTE, recente.getStatus());
    }

    @Test
    void listagemJaMostraOsExpirados() {
        salvarCriadoHa(10);

        assertEquals(1, service.listarPorStatus(StatusOrcamento.EXPIRADO).size());
        assertEquals(0, service.listarPorStatus(StatusOrcamento.PENDENTE).size());
    }

    @Test
    void naoAprovaOrcamentoExpirado() {
        Orcamento orcamento = salvarCriadoHa(10);

        IllegalArgumentException erro = assertThrows(
                IllegalArgumentException.class,
                () -> service.aprovar(orcamento.getId()));

        assertEquals(
                "Somente orçamentos pendentes podem mudar de status (este está EXPIRADO)",
                erro.getMessage());
    }
}
