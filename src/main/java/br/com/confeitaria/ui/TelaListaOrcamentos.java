package br.com.confeitaria.ui;

import br.com.confeitaria.service.OrcamentoService;

import java.io.PrintStream;
import java.util.Scanner;

/** US05 — tela "Listar orçamentos". */
public class TelaListaOrcamentos {

    private final OrcamentoService orcamentoService;
    private final Scanner entrada;
    private final PrintStream saida;

    public TelaListaOrcamentos(OrcamentoService orcamentoService,
                               Scanner entrada,
                               PrintStream saida) {
        this.orcamentoService = orcamentoService;
        this.entrada = entrada;
        this.saida = saida;
    }

    public void executar() {
        saida.println("Em construção (US05).");
    }
}