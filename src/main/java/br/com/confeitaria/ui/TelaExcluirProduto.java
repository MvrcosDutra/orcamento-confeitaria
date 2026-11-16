
package br.com.confeitaria.ui;

import br.com.confeitaria.domain.Produto;
import br.com.confeitaria.service.ProdutoService;

import java.io.PrintStream;
import java.util.Scanner;

public class TelaExcluirProduto {

    private final ProdutoService produtoService;
    private final Scanner entrada;
    private final PrintStream saida;

    public TelaExcluirProduto(ProdutoService produtoService, Scanner entrada, PrintStream saida) {
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

        saida.print("Confirma a exclusão de \"" + produto.getNome() + "\"? (S/N): ");

        if (!entrada.nextLine().trim().equalsIgnoreCase("S")) {
            saida.println("Exclusão cancelada.");
            return;
        }

        produtoService.excluir(produto.getId());
        saida.println("Produto excluído.");
    }
}
