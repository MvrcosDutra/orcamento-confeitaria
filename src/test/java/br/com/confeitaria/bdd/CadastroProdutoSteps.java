package br.com.confeitaria.bdd;

import br.com.confeitaria.domain.Categoria;
import br.com.confeitaria.domain.Produto;
import br.com.confeitaria.infra.ProdutoRepositoryEmMemoria;
import br.com.confeitaria.service.ProdutoService;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CadastroProdutoSteps {

    private ProdutoService service;
    private String mensagemErro;

    @Dado("que não há produtos cadastrados")
    public void queNaoHaProdutosCadastrados() {
        service = new ProdutoService(new ProdutoRepositoryEmMemoria());
        mensagemErro = null;
    }

    @Quando("eu cadastro o produto {string} da categoria {string} com preço {string}")
    public void euCadastroOProduto(String nome, String categoria, String preco) {
        try {
            service.cadastrar(nome, Categoria.valueOf(categoria), new BigDecimal(preco.replace(",", ".")));
        } catch (IllegalArgumentException e) {
            mensagemErro = e.getMessage();
        }
    }

    @Entao("o produto {string} aparece na lista de produtos")
    public void oProdutoApareceNaLista(String nome) {
        assertTrue(service.listar().stream().map(Produto::getNome).anyMatch(nome::equals));
    }

    @Entao("vejo a mensagem de erro {string}")
    public void vejoAMensagemDeErro(String mensagem) {
        assertEquals(mensagem, mensagemErro);
    }

    @E("a lista de produtos está vazia")
    public void aListaDeProdutosEstaVazia() {
        assertTrue(service.listar().isEmpty());
    }
}
