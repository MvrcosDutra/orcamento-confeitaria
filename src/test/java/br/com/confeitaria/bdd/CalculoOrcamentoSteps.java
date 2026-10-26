
package br.com.confeitaria.bdd;

import br.com.confeitaria.domain.Categoria;
import br.com.confeitaria.domain.ItemOrcamento;
import br.com.confeitaria.domain.Produto;
import br.com.confeitaria.service.CalculadoraOrcamento;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculoOrcamentoSteps {

    private final CalculadoraOrcamento calculadora =
            new CalculadoraOrcamento();

    private final LocalDate hoje = LocalDate.of(2026, 11, 2);

    private List<ItemOrcamento> itens;
    private BigDecimal desconto;
    private BigDecimal taxaEntrega;
    private BigDecimal total;
    private String mensagemErro;

    @Dado("um pedido de {int} unidades de {string} custando {string} cada")
    public void umPedido(int quantidade, String nome, String preco) {
        Produto produto = new Produto(
                nome, Categoria.BOLO, valor(preco)
        );

        itens = List.of(
                new ItemOrcamento(produto, quantidade, "", false)
        );
    }

    @Dado("um pedido com um bolo personalizado")
    public void umPedidoComBoloPersonalizado() {
        Produto bolo = new Produto(
                "Bolo temático",
                Categoria.BOLO,
                new BigDecimal("150")
        );

        itens = List.of(
                new ItemOrcamento(bolo, 1, "Tema futebol", true)
        );
    }

    @Quando("calculo o orçamento para retirada no balcão")
    public void calculoParaRetirada() {
        calcular(null);
    }

    @Quando("calculo o orçamento para entrega a {int} km")
    public void calculoParaEntrega(int km) {
        calcular(new BigDecimal(km));
    }

    @Quando("a entrega é marcada para daqui a {int} dias")
    public void entregaMarcadaPara(int dias) {
        try {
            calculadora.validarAntecedencia(
                    hoje, hoje.plusDays(dias), true
            );
        } catch (IllegalArgumentException e) {
            mensagemErro = e.getMessage();
        }
    }

    @Entao("o desconto é {string}")
    public void oDescontoE(String esperado) {
        assertEquals(valor(esperado), desconto);
    }

    @Entao("a taxa de entrega é {string}")
    public void aTaxaDeEntregaE(String esperado) {
        assertEquals(valor(esperado), taxaEntrega);
    }

    @Entao("o total é {string}")
    public void oTotalE(String esperado) {
        assertEquals(valor(esperado), total);
    }

    @Entao("o sistema recusa com a mensagem {string}")
    public void oSistemaRecusa(String mensagem) {
        assertEquals(mensagem, mensagemErro);
    }

    private void calcular(BigDecimal distanciaKm) {
        BigDecimal subtotal = calculadora.calcularSubtotal(itens);
        desconto = calculadora.calcularDesconto(subtotal);
        taxaEntrega = calculadora.calcularTaxaEntrega(distanciaKm);

        total = subtotal.subtract(desconto).add(taxaEntrega);
    }

    private BigDecimal valor(String texto) {
        return new BigDecimal(texto.replace(",", "."))
                .setScale(2);
    }
}