package br.com.confeitaria.ui;

import br.com.confeitaria.service.OrcamentoService;
import br.com.confeitaria.service.ProdutoService;

import java.io.PrintStream;
import java.util.Scanner;

/** US03 — tela "Novo orçamento". */
public class TelaNovoOrcamento {

    private final ProdutoService produtoService;
    private final OrcamentoService orcamentoService;
    private final Scanner entrada;
    private final PrintStream saida;

    public TelaNovoOrcamento(ProdutoService produtoService,
                             OrcamentoService orcamentoService,
                             Scanner entrada,
                             PrintStream saida) {
        this.produtoService = produtoService;
        this.orcamentoService = orcamentoService;
        this.entrada = entrada;
        this.saida = saida;
    }

    public void executar() {
        saida.println("Em construção (US03).");
    }
}