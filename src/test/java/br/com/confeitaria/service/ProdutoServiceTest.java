package br.com.confeitaria.service;

import br.com.confeitaria.domain.Categoria;
import br.com.confeitaria.domain.Produto;
import br.com.confeitaria.infra.ProdutoRepositoryEmMemoria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProdutoServiceTest {

    private ProdutoService service;

    @BeforeEach
    void setUp() {
        service = new ProdutoService(new ProdutoRepositoryEmMemoria());
    }

    @Test
    void deveCadastrarProdutoValido() {
        Produto produto = service.cadastrar("Bolo de chocolate", Categoria.BOLO, new BigDecimal("80"));

        assertNotNull(produto.getId());
        assertEquals("Bolo de chocolate", produto.getNome());
        assertEquals(new BigDecimal("80.00"), produto.getPreco());
    }

    @Test
    void deveRemoverEspacosDoNome() {
        Produto produto = service.cadastrar("  Brigadeiro  ", Categoria.DOCE, new BigDecimal("2.50"));

        assertEquals("Brigadeiro", produto.getNome());
    }

    @Test
    void deveRejeitarNomeVazio() {
        IllegalArgumentException erro = assertThrows(IllegalArgumentException.class,
                () -> service.cadastrar("   ", Categoria.BOLO, new BigDecimal("10")));

        assertEquals("Nome do produto é obrigatório", erro.getMessage());
    }

    @Test
    void deveRejeitarNomeNulo() {
        assertThrows(IllegalArgumentException.class,
                () -> service.cadastrar(null, Categoria.BOLO, new BigDecimal("10")));
    }

    @Test
    void deveRejeitarCategoriaNula() {
        assertThrows(IllegalArgumentException.class,
                () -> service.cadastrar("Coxinha", null, new BigDecimal("5")));
    }

    @Test
    void deveRejeitarPrecoZero() {
        IllegalArgumentException erro = assertThrows(IllegalArgumentException.class,
                () -> service.cadastrar("Coxinha", Categoria.SALGADO, BigDecimal.ZERO));

        assertEquals("Preço deve ser maior que zero", erro.getMessage());
    }

    @Test
    void deveRejeitarPrecoNegativo() {
        assertThrows(IllegalArgumentException.class,
                () -> service.cadastrar("Coxinha", Categoria.SALGADO, new BigDecimal("-1")));
    }

    @Test
    void deveRejeitarPrecoNulo() {
        assertThrows(IllegalArgumentException.class,
                () -> service.cadastrar("Coxinha", Categoria.SALGADO, null));
    }

    @Test
    void deveRejeitarNomeDuplicadoIgnorandoMaiusculas() {
        service.cadastrar("Brigadeiro", Categoria.DOCE, new BigDecimal("2.50"));

        assertThrows(IllegalArgumentException.class,
                () -> service.cadastrar("BRIGADEIRO", Categoria.DOCE, new BigDecimal("3")));
    }

    @Test
    void deveListarProdutosEmOrdemAlfabetica() {
        service.cadastrar("Coxinha", Categoria.SALGADO, new BigDecimal("5"));
        service.cadastrar("bolo de cenoura", Categoria.BOLO, new BigDecimal("60"));
        service.cadastrar("Brigadeiro", Categoria.DOCE, new BigDecimal("2.50"));

        List<Produto> produtos = service.listar();

        assertEquals(List.of("bolo de cenoura", "Brigadeiro", "Coxinha"),
                produtos.stream().map(Produto::getNome).toList());
    }

    @Test
    void deveRetornarListaVaziaSemProdutos() {
        assertTrue(service.listar().isEmpty());
    }
    @Test
void deveBuscarProdutoPorId() {
    Produto salvo = service.cadastrar(
            "Coxinha",
            Categoria.SALGADO,
            new BigDecimal("5")
    );

    assertEquals(
            "Coxinha",
            service.buscarPorId(salvo.getId()).getNome()
    );
}

@Test
void deveRejeitarBuscaPorIdInexistente() {
    IllegalArgumentException erro = assertThrows(
            IllegalArgumentException.class,
            () -> service.buscarPorId(99L)
    );

    assertEquals(
            "Produto não encontrado",
            erro.getMessage()
    );
}
}
