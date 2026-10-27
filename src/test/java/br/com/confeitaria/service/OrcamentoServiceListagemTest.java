package br.com.confeitaria.service;

import br.com.confeitaria.domain.Cliente;
import br.com.confeitaria.domain.Orcamento;
import br.com.confeitaria.domain.StatusOrcamento;
import br.com.confeitaria.infra.OrcamentoRepositoryEmMemoria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Testes da US05 — listar orçamentos. Os orçamentos são salvos direto no repositório. */
class OrcamentoServiceListagemTest {

    private static final LocalDate HOJE = LocalDate.of(2026, 11, 2);

    private OrcamentoRepositoryEmMemoria repository;
    private OrcamentoService service;

    @BeforeEach
    void setUp() {
        repository = new OrcamentoRepositoryEmMemoria();

        service = new OrcamentoService(
                repository,
                new CalculadoraOrcamento(),
                Clock.systemDefaultZone()
        );
    }

    private Orcamento orcamentoDe(String cliente, LocalDate entrega) {
        return new Orcamento(
                new Cliente(cliente, "(11) 90000-0000"),
                List.of(),
                HOJE,
                entrega,
                null
        );
    }

    @Test
    void semOrcamentosDeveRetornarListaVazia() {
        assertTrue(service.listar().isEmpty());
    }

    @Test
    void deveOrdenarPelaDataDeEntregaMaisProxima() {
        repository.salvar(orcamentoDe("Carla", HOJE.plusDays(10)));
        repository.salvar(orcamentoDe("Ana", HOJE.plusDays(3)));
        repository.salvar(orcamentoDe("Bruno", HOJE.plusDays(5)));

        List<String> clientes = service.listar()
                .stream()
                .map(o -> o.getCliente().getNome())
                .toList();

        assertEquals(List.of("Ana", "Bruno", "Carla"), clientes);
    }

    @Test
    void deveFiltrarPorStatusPendente() {
        repository.salvar(orcamentoDe("Ana", HOJE.plusDays(3)));
        repository.salvar(orcamentoDe("Bruno", HOJE.plusDays(5)));

        assertEquals(
                2,
                service.listarPorStatus(StatusOrcamento.PENDENTE).size()
        );
    }

    @Test
    void filtroSemResultadosDeveRetornarListaVazia() {
        repository.salvar(orcamentoDe("Ana", HOJE.plusDays(3)));

        assertTrue(
                service.listarPorStatus(StatusOrcamento.APROVADO).isEmpty()
        );
    }
}