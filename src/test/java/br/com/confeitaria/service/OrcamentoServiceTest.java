
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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OrcamentoServiceTest {

    private static final LocalDate HOJE = LocalDate.of(2026, 11, 2);

    private OrcamentoService service;

    private final Produto bolo = new Produto(
            "Bolo de chocolate",
            Categoria.BOLO,
            new BigDecimal("80")
    );

    private final Cliente cliente = new Cliente(
            "Ana Souza",
            "(11) 99999-0000"
    );

    @BeforeEach
    void setUp() {
        Clock relogio = Clock.fixed(
                Instant.parse("2026-11-02T12:00:00Z"),
                ZoneId.of("America/Sao_Paulo")
        );

        service = new OrcamentoService(
                new OrcamentoRepositoryEmMemoria(),
                new CalculadoraOrcamento(),
                relogio
        );
    }

    private List<ItemOrcamento> quatroBolos() {
        return List.of(new ItemOrcamento(bolo, 4, "", false));
    }

    @Test
    void deveCriarOrcamentoPendenteComValoresCalculados() {
        Orcamento orcamento = service.criar(
                cliente, quatroBolos(), HOJE.plusDays(3), new BigDecimal("10")
        );

        assertNotNull(orcamento.getId());
        assertEquals(StatusOrcamento.PENDENTE, orcamento.getStatus());
        assertEquals(HOJE, orcamento.getDataCriacao());
        assertEquals(new BigDecimal("320.00"), orcamento.getSubtotal());
        assertEquals(new BigDecimal("16.00"), orcamento.getDesconto());
        assertEquals(new BigDecimal("20.00"), orcamento.getTaxaEntrega());
        assertEquals(new BigDecimal("324.00"), orcamento.getTotal());
    }

    @Test
    void retiradaNoBalcaoNaoCobraEntrega() {
        Orcamento orcamento = service.criar(
                cliente, quatroBolos(), HOJE.plusDays(3), null
        );

        assertEquals(new BigDecimal("304.00"), orcamento.getTotal());
        assertEquals(BigDecimal.ZERO.setScale(2), orcamento.getTaxaEntrega());
    }

    @Test
    void deveRejeitarClienteSemNome() {
        IllegalArgumentException erro = assertThrows(
                IllegalArgumentException.class,
                () -> service.criar(
                        new Cliente(" ", "(11) 99999-0000"),
                        quatroBolos(), HOJE.plusDays(3), null
                )
        );

        assertEquals("Nome do cliente é obrigatório", erro.getMessage());
    }

    @Test
    void deveRejeitarClienteSemTelefone() {
        IllegalArgumentException erro = assertThrows(
                IllegalArgumentException.class,
                () -> service.criar(
                        new Cliente("Ana", ""),
                        quatroBolos(), HOJE.plusDays(3), null
                )
        );

        assertEquals("Telefone do cliente é obrigatório", erro.getMessage());
    }

    @Test
    void deveRejeitarOrcamentoSemItens() {
        IllegalArgumentException erro = assertThrows(
                IllegalArgumentException.class,
                () -> service.criar(
                        cliente, List.of(), HOJE.plusDays(3), null
                )
        );

        assertEquals(
                "O orçamento precisa de pelo menos um item",
                erro.getMessage()
        );
    }

    @Test
    void deveRejeitarQuantidadeZero() {
        List<ItemOrcamento> itens =
                List.of(new ItemOrcamento(bolo, 0, "", false));

        IllegalArgumentException erro = assertThrows(
                IllegalArgumentException.class,
                () -> service.criar(cliente, itens, HOJE.plusDays(3), null)
        );

        assertEquals("Quantidade deve ser maior que zero", erro.getMessage());
    }

    @Test
    void deveRejeitarSemDataDeEntrega() {
        IllegalArgumentException erro = assertThrows(
                IllegalArgumentException.class,
                () -> service.criar(cliente, quatroBolos(), null, null)
        );

        assertEquals("Data de entrega é obrigatória", erro.getMessage());
    }

    @Test
    void deveRejeitarBoloPersonalizadoSemAntecedencia() {
        List<ItemOrcamento> itens = List.of(
                new ItemOrcamento(bolo, 1, "Tema princesa", true)
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> service.criar(cliente, itens, HOJE.plusDays(3), null)
        );
    }
}
