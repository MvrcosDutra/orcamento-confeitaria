
package br.com.confeitaria.service;

import br.com.confeitaria.domain.Categoria;
import br.com.confeitaria.domain.Produto;
import br.com.confeitaria.infra.ProdutoRepositoryEmMemoria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProdutoServiceEdicaoTest {

    private ProdutoService service;
    private Produto bolo;

    @BeforeEach
    void setUp() {
        service = new ProdutoService(new ProdutoRepositoryEmMemoria());
        bolo = service.cadastrar("Bolo de chocolate", Categoria.BOLO, new BigDecimal("80"));
        service.cadastrar("Brigadeiro", Categoria.DOCE, new BigDecimal("2.50"));
    }

    @Test
    void deveEditarPreco() {
        service.editar(bolo.getId(), "Bolo de chocolate", Categoria.BOLO, new BigDecimal("95"));

        assertEquals(new BigDecimal("95.00"), service.buscarPorId(bolo.getId()).getPreco());
    }

    @Test
    void deveEditarNomeECategoria() {
        service.editar(bolo.getId(), "Torta de chocolate", Categoria.DOCE, new BigDecimal("80"));

        Produto editado = service.buscarPorId(bolo.getId());
        assertEquals("Torta de chocolate", editado.getNome());
        assertEquals(Categoria.DOCE, editado.getCategoria());
    }

    @Test
    void podeManterOMesmoNome() {
        Produto editado = service.editar(bolo.getId(), "BOLO DE CHOCOLATE", Categoria.BOLO,
                new BigDecimal("85"));

        assertEquals(new BigDecimal("85.00"), editado.getPreco());
    }

    @Test
    void naoPodeUsarNomeDeOutroProduto() {
        IllegalArgumentException erro = assertThrows(IllegalArgumentException.class,
                () -> service.editar(bolo.getId(), "brigadeiro", Categoria.BOLO,
                        new BigDecimal("80")));

        assertEquals("Já existe um produto com o nome \"brigadeiro\"", erro.getMessage());
    }

    @Test
    void naoPodeEditarComPrecoZero() {
        IllegalArgumentException erro = assertThrows(IllegalArgumentException.class,
                () -> service.editar(bolo.getId(), "Bolo de chocolate", Categoria.BOLO,
                        BigDecimal.ZERO));

        assertEquals("Preço deve ser maior que zero", erro.getMessage());
    }

    @Test
    void codigoInexistenteDaErro() {
        IllegalArgumentException erro = assertThrows(IllegalArgumentException.class,
                () -> service.editar(99L, "Qualquer", Categoria.BOLO, new BigDecimal("10")));

        assertEquals("Produto não encontrado", erro.getMessage());
    }
}
