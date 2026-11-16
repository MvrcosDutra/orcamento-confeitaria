
package br.com.confeitaria.service;

import br.com.confeitaria.domain.Categoria;
import br.com.confeitaria.domain.Produto;
import br.com.confeitaria.infra.ProdutoRepositoryEmMemoria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProdutoServiceBuscaTest {

    private ProdutoService service;

    @BeforeEach
    void setUp() {
        service = new ProdutoService(new ProdutoRepositoryEmMemoria());
        service.cadastrar("Bolo de chocolate", Categoria.BOLO, new BigDecimal("80"));
        service.cadastrar("Brigadeiro", Categoria.DOCE, new BigDecimal("2.50"));
        service.cadastrar("Beijinho", Categoria.DOCE, new BigDecimal("2.50"));
        service.cadastrar("Coxinha", Categoria.SALGADO, new BigDecimal("5"));
    }

    private List<String> nomes(List<Produto> produtos) {
        return produtos.stream().map(Produto::getNome).toList();
    }

    @Test
    void encontraPorTrechoDoNome() {
        assertEquals(List.of("Bolo de chocolate"), nomes(service.buscar("choc")));
    }

    @Test
    void naoDiferenciaMaiusculasEMinusculas() {
        assertEquals(List.of("Coxinha"), nomes(service.buscar("COXI")));
    }

    @Test
    void encontraPelaCategoriaEmOrdemAlfabetica() {
        assertEquals(List.of("Beijinho", "Brigadeiro"), nomes(service.buscar("doce")));
    }

    @Test
    void ignoraEspacosAntesEDepois() {
        assertEquals(List.of("Coxinha"), nomes(service.buscar("   coxinha   ")));
    }

    @Test
    void buscaSemResultadoDevolveListaVazia() {
        assertTrue(service.buscar("torta").isEmpty());
    }

    @Test
    void termoVazioEhRecusado() {
        IllegalArgumentException erro = assertThrows(IllegalArgumentException.class,
                () -> service.buscar(" "));

        assertEquals("Digite parte do nome ou a categoria para buscar", erro.getMessage());
    }
}
