package br.com.confeitaria.ui;

import br.com.confeitaria.service.ProdutoService;

import java.io.PrintStream;
import java.util.Scanner;

/** US06 — tela "Editar produto". */
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
        saida.println("Em construção (US06).");
    }
}
