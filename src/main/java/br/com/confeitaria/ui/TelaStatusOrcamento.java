
package br.com.confeitaria.ui;

import br.com.confeitaria.domain.Orcamento;
import br.com.confeitaria.domain.StatusOrcamento;
import br.com.confeitaria.service.OrcamentoService;

import java.io.PrintStream;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

/** US08 — tela "Aprovar ou recusar orçamento". */
public class TelaStatusOrcamento {

    private static final DateTimeFormatter FORMATO_DATA =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final OrcamentoService orcamentoService;
    private final Scanner entrada;
    private final PrintStream saida;

    public TelaStatusOrcamento(
            OrcamentoService orcamentoService,
            Scanner entrada,
            PrintStream saida) {
        this.orcamentoService = orcamentoService;
        this.entrada = entrada;
        this.saida = saida;
    }

    public void executar() {
        List<Orcamento> pendentes =
                orcamentoService.listarPorStatus(StatusOrcamento.PENDENTE);

        if (pendentes.isEmpty()) {
            saida.println("Nenhum orçamento pendente.");
            return;
        }

        saida.println("Orçamentos pendentes:");

        for (Orcamento o : pendentes) {
            saida.println(" " + o.getId() + " - "
                    + o.getCliente().getNome()
                    + " - entrega " + o.getDataEntrega().format(FORMATO_DATA)
                    + " - R$ " + o.getTotal().toString().replace(".", ","));
        }

        saida.print("Código do orçamento: ");

        Long id;
        try {
            id = Long.parseLong(entrada.nextLine().trim());
        } catch (NumberFormatException e) {
            saida.println("Código inválido. Digite apenas números.");
            return;
        }

        saida.print("(A)provar ou (R)ecusar? ");
        String opcao = entrada.nextLine().trim();

        try {
            Orcamento orcamento;

            if (opcao.equalsIgnoreCase("A")) {
                orcamento = orcamentoService.aprovar(id);
            } else if (opcao.equalsIgnoreCase("R")) {
                orcamento = orcamentoService.recusar(id);
            } else {
                saida.println("Opção inválida. Digite A ou R.");
                return;
            }

            saida.println("Orçamento nº " + orcamento.getId()
                    + " agora está " + orcamento.getStatus() + ".");

        } catch (IllegalArgumentException e) {
            saida.println("Erro: " + e.getMessage());
        }
    }
}
