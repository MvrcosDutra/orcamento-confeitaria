package br.com.confeitaria.ui;

import br.com.confeitaria.service.ProdutoService;

import java.io.PrintStream;
import java.util.Scanner;

/** US09 — tela "Buscar produto". */
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
        saida.println("Em construção (US09).");
    }
}
