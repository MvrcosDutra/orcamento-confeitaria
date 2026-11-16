
package br.com.confeitaria.ui;

import br.com.confeitaria.domain.Produto;
import br.com.confeitaria.service.ProdutoService;

import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

public class TelaBuscarProduto {

    private final ProdutoService produtoService;
    private final Scanner entrada;
    private final PrintStream saida;

    public TelaBuscarProduto(ProdutoService produtoService, Scanner entrada, PrintStream saida) {
        this.produtoService = produtoService;
        this.entrada = entrada;
        this.saida = saida;
    }

    public void executar() {
        saida.print("Buscar (parte do nome ou categoria): ");
        List<Produto> encontrados;

        try {
            encontrados = produtoService.buscar(entrada.nextLine());
        } catch (IllegalArgumentException e) {
            saida.println("Erro: " + e.getMessage());
            return;
        }

        if (encontrados.isEmpty()) {
            saida.println("Nenhum produto encontrado.");
            return;
        }

        saida.printf("%-6s %-30s %-10s %10s%n", "Código", "Nome", "Categoria", "Preço");

        for (Produto p : encontrados) {
            saida.printf("%-6d %-30s %-10s %10s%n",
                    p.getId(),
                    p.getNome(),
                    p.getCategoria().getDescricao(),
                    "R$ " + p.getPreco().toString().replace(".", ","));
        }
    }
}
