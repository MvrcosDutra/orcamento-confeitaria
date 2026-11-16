
package br.com.confeitaria.ui;

import br.com.confeitaria.domain.Categoria;
import br.com.confeitaria.domain.Produto;
import br.com.confeitaria.service.ProdutoService;

import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.Scanner;

public class TelaEditarProduto {

    private final ProdutoService produtoService;
    private final Scanner entrada;
    private final PrintStream saida;

    public TelaEditarProduto(ProdutoService produtoService, Scanner entrada, PrintStream saida) {
        this.produtoService = produtoService;
        this.entrada = entrada;
        this.saida = saida;
    }

    public void executar() {
        saida.print("Código do produto: ");
        Produto produto;

        try {
            produto = produtoService.buscarPorId(Long.parseLong(entrada.nextLine().trim()));
        } catch (NumberFormatException e) {
            saida.println("Código inválido. Digite apenas números.");
            return;
        } catch (IllegalArgumentException e) {
            saida.println("Erro: " + e.getMessage());
            return;
        }

        saida.println("Editando: " + produto.getNome() + " (" + produto.getCategoria().getDescricao()
                + ", R$ " + produto.getPreco().toString().replace(".", ",") + ")");
        saida.println("Deixe em branco e aperte Enter para manter o valor atual.");

        saida.print("Novo nome: ");
        String nome = entrada.nextLine().trim();

        if (nome.isEmpty()) {
            nome = produto.getNome();
        }

        Categoria[] categorias = Categoria.values();

        for (int i = 0; i < categorias.length; i++) {
            saida.println((i + 1) + " - " + categorias[i].getDescricao());
        }

        saida.print("Nova categoria (número): ");
        String textoCategoria = entrada.nextLine().trim();
        Categoria categoria = produto.getCategoria();

        if (!textoCategoria.isEmpty()) {
            try {
                categoria = categorias[Integer.parseInt(textoCategoria) - 1];
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                saida.println("Categoria inválida. Digite um número de 1 a " + categorias.length + ".");
                return;
            }
        }

        saida.print("Novo preço (ex.: 45,90): ");
        String textoPreco = entrada.nextLine().trim();
        BigDecimal preco = produto.getPreco();

        if (!textoPreco.isEmpty()) {
            try {
                preco = new BigDecimal(textoPreco.replace(",", "."));
            } catch (NumberFormatException e) {
                saida.println("Preço inválido. Use apenas números, por exemplo 45,90.");
                return;
            }
        }

        try {
            produtoService.editar(produto.getId(), nome, categoria, preco);
            saida.println("Produto atualizado.");
        } catch (IllegalArgumentException e) {
            saida.println("Erro: " + e.getMessage());
        }
    }
}
