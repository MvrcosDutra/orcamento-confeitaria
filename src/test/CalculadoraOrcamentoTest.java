package br.com.confeitaria.service;
import br.com.confeitaria.domain.Categoria;
import br.com.confeitaria.domain.ItemOrcamento;
import br.com.confeitaria.domain.Produto;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow; 
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Testes da US04 usando análise de valor limite.
 * Testamos as fronteiras das regras RN01, RN02 e RN03.
 */
class CalculadoraOrcamentoTest {

    private final CalculadoraOrcamento calculadora =
            new CalculadoraOrcamento();

    private final LocalDate hoje = LocalDate.of(2026, 11, 2);

    // Subtotal

    @Test
    void subtotalDeveSomarPrecoVezesQuantidadeDeCadaItem() {
        Produto bolo = new Produto(
                "Bolo de chocolate",
                Categoria.BOLO,
                new BigDecimal("80"));

        Produto brigadeiro = new Produto(
                "Brigadeiro",
                Categoria.DOCE,
                new BigDecimal("2.50"));

        List<ItemOrcamento> itens = List.of(
                new ItemOrcamento(bolo, 2, "", false),
                new ItemOrcamento(brigadeiro, 100, "", false));

        assertEquals(
                new BigDecimal("410.00"),
                calculadora.calcularSubtotal(itens));
    }

    @Test
    void subtotalDeListaVaziaDeveSerZero() {
        assertEquals(
                new BigDecimal("0.00"),
                calculadora.calcularSubtotal(List.of()));
    }

    // RN01: desconto progressivo

    @Test
    void semDescontoAbaixoDe300() {
        assertEquals(
                new BigDecimal("0.00"),
                calculadora.calcularDesconto(
                        new BigDecimal("299.99")));
    }

    @Test
    void desconto5PorCentoEmExatamente300() {
        assertEquals(
                new BigDecimal("15.00"),
                calculadora.calcularDesconto(
                        new BigDecimal("300.00")));
    }

    @Test
    void desconto5PorCentoLogoAbaixoDe600() {
        assertEquals(
                new BigDecimal("30.00"),
                calculadora.calcularDesconto(
                        new BigDecimal("599.99")));
    }

    @Test
    void desconto10PorCentoEmExatamente600() {
        assertEquals(
                new BigDecimal("60.00"),
                calculadora.calcularDesconto(
                        new BigDecimal("600.00")));
    }

    // RN02: taxa de entrega

    @Test
    void retiradaNoBalcaoNaoTemTaxa() {
        assertEquals(
                new BigDecimal("0.00"),
                calculadora.calcularTaxaEntrega(null));
    }

    @Test
    void entregaEmExatamente5KmCusta10() {
        assertEquals(
                new BigDecimal("10.00"),
                calculadora.calcularTaxaEntrega(
                        new BigDecimal("5")));
    }

    @Test
    void entregaLogoAcimaDe5KmCusta20() {
        assertEquals(
                new BigDecimal("20.00"),
                calculadora.calcularTaxaEntrega(
                        new BigDecimal("5.01")));
    }

    @Test
    void entregaEmExatamente15KmCusta20() {
        assertEquals(
                new BigDecimal("20.00"),
                calculadora.calcularTaxaEntrega(
                        new BigDecimal("15")));
    }

    @Test
    void entregaAcimaDe15KmNaoEAceita() {
        assertThrows(
                IllegalArgumentException.class,
                () -> calculadora.calcularTaxaEntrega(
                        new BigDecimal("15.01")));
    }

    @Test
    void distanciaNegativaNaoEAceita() {
        assertThrows(
                IllegalArgumentException.class,
                () -> calculadora.calcularTaxaEntrega(
                        new BigDecimal("-1")));
    }

    // RN03: antecedência mínima

    @Test
    void pedidoComumCom1DiaDeAntecedenciaEhRecusado() {
        IllegalArgumentException erro = assertThrows(
                IllegalArgumentException.class,
                () -> calculadora.validarAntecedencia(
                        hoje, hoje.plusDays(1), false));

        assertEquals(
                "Entrega exige no mínimo 2 dias de antecedência",
                erro.getMessage());
    }

    @Test
    void pedidoComumCom2DiasDeAntecedenciaEhAceito() {
        assertDoesNotThrow(
                () -> calculadora.validarAntecedencia(
                        hoje, hoje.plusDays(2), false));
    }

    @Test
    void boloPersonalizadoCom4DiasDeAntecedenciaEhRecusado() {
        IllegalArgumentException erro = assertThrows(
                IllegalArgumentException.class,
                () -> calculadora.validarAntecedencia(
                        hoje, hoje.plusDays(4), true));

        assertEquals(
                "Entrega exige no mínimo 5 dias de antecedência",
                erro.getMessage());
    }

    @Test
    void boloPersonalizadoCom5DiasDeAntecedenciaEhAceito() {
        assertDoesNotThrow(
                () -> calculadora.validarAntecedencia(
                        hoje, hoje.plusDays(5), true));
    }
}