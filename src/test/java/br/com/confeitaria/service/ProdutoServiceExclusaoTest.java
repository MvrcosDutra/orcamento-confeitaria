
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

class ProdutoServiceExclusaoTest {

    private ProdutoService service;
    private Produto bolo;
    private Produto brigadeiro;

    @BeforeEach
    void setUp() {
        service = new ProdutoService(new ProdutoRepositoryEmMemoria());
        bolo = service.cadastrar("Bolo de chocolate", Categoria.BOLO, new BigDecimal("80"));
        brigadeiro = service.cadastrar("Brigadeiro", Categoria.DOCE, new BigDecimal("2.50"));
    }

    @Test
    void produtoExcluidoSaiDaListagem() {
        service.excluir(bolo.getId());

        assertEquals(List.of("Brigadeiro"),
                service.listar().stream().map(Produto::getNome).toList());
    }

    @Test
    void excluirCodigoInexistenteDaErro() {
        IllegalArgumentException erro = assertThrows(IllegalArgumentException.class,
                () -> service.excluir(99L));

        assertEquals("Produto não encontrado", erro.getMessage());
    }

    @Test
    void excluirUmProdutoNaoAfetaOsOutros() {
        service.excluir(bolo.getId());

        assertEquals("Brigadeiro", service.buscarPorId(brigadeiro.getId()).getNome());
    }
}
