package br.com.confeitaria.bdd;

import br.com.confeitaria.domain.Categoria;
import br.com.confeitaria.domain.Cliente;
import br.com.confeitaria.domain.ItemOrcamento;
import br.com.confeitaria.domain.Orcamento;
import br.com.confeitaria.domain.Produto;
import br.com.confeitaria.domain.StatusOrcamento;
import br.com.confeitaria.infra.OrcamentoRepositoryEmMemoria;
import br.com.confeitaria.service.CalculadoraOrcamento;
import br.com.confeitaria.service.OrcamentoService;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StatusOrcamentoSteps {

    private static final LocalDate HOJE = LocalDate.of(2026, 11, 10);

    private OrcamentoService service;
    private Orcamento orcamento;
    private String mensagemErro;

    @Dado("um orçamento pendente criado há {int} dia(s)")
    public void umOrcamentoPendenteCriadoHa(int dias) {
        OrcamentoRepositoryEmMemoria repository = new OrcamentoRepositoryEmMemoria();

        Clock relogio = Clock.fixed(
                Instant.parse("2026-11-10T12:00:00Z"),
                ZoneId.of("America/Sao_Paulo")
        );

        service = new OrcamentoService(
                repository,
                new CalculadoraOrcamento(),
                relogio
        );

        Produto bolo = new Produto(
                "Bolo de chocolate",
                Categoria.BOLO,
                new BigDecimal("80")
        );

        orcamento = repository.salvar(
                new Orcamento(
                        new Cliente("Ana", "(31) 99999-0000"),
                        List.of(new ItemOrcamento(bolo, 1, "", false)),
                        HOJE.minusDays(dias),
                        HOJE.plusDays(5),
                        null
                )
        );

        mensagemErro = null;
    }

    @Quando("o atendente aprova o orçamento")
    public void oAtendenteAprova() {
        try {
            service.aprovar(orcamento.getId());
        } catch (IllegalArgumentException e) {
            mensagemErro = e.getMessage();
        }
    }

    @Quando("o atendente recusa o orçamento")
    public void oAtendenteRecusa() {
        service.recusar(orcamento.getId());
    }

    @Quando("o atendente consulta os orçamentos")
    public void oAtendenteConsulta() {
        service.listar();
    }

    @Entao("o status do orçamento é {string}")
    public void oStatusE(String status) {
        assertEquals(StatusOrcamento.valueOf(status), orcamento.getStatus());
    }

    @Entao("a alteração é bloqueada com a mensagem {string}")
    public void aAlteracaoEBloqueada(String mensagem) {
        assertEquals(mensagem, mensagemErro);
    }
}
