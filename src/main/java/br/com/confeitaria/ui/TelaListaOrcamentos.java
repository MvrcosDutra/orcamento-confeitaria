package br.com.confeitaria.ui;

import br.com.confeitaria.domain.Orcamento;
import br.com.confeitaria.domain.StatusOrcamento;
import br.com.confeitaria.service.OrcamentoService;

import java.io.PrintStream;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

/** US05 — tela "Listar orçamentos". */
public class TelaListaOrcamentos {

    private static final DateTimeFormatter FORMATO_DATA =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

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

        saida.print(
                "Filtrar por status? (Enter = todos, P = pendente, " +
                "A = aprovado, R = recusado, E = expirado): "
        );

        List<Orcamento> orcamentos;

        switch (entrada.nextLine().trim().toUpperCase()) {

            case "" -> orcamentos =
                    orcamentoService.listar();

            case "P" -> orcamentos =
                    orcamentoService.listarPorStatus(
                            StatusOrcamento.PENDENTE);

            case "A" -> orcamentos =
                    orcamentoService.listarPorStatus(
                            StatusOrcamento.APROVADO);

            case "R" -> orcamentos =
                    orcamentoService.listarPorStatus(
                            StatusOrcamento.RECUSADO);

            case "E" -> orcamentos =
                    orcamentoService.listarPorStatus(
                            StatusOrcamento.EXPIRADO);

            default -> {
                saida.println(
                        "Opção inválida. Use Enter, P, A, R ou E.");
                return;
            }
        }

        if (orcamentos.isEmpty()) {
            saida.println("Nenhum orçamento encontrado.");
            return;
        }

        saida.printf(
                "%-6s %-25s %-12s %12s %-10s%n",
                "Código",
                "Cliente",
                "Entrega",
                "Total",
                "Status"
        );

        for (Orcamento o : orcamentos) {

            saida.printf(
                    "%-6d %-25s %-12s %12s %-10s%n",
                    o.getId(),
                    o.getCliente().getNome(),
                    o.getDataEntrega().format(FORMATO_DATA),
                    "R$ " + o.getTotal().toString().replace(".", ","),
                    o.getStatus()
            );
        }
    }
}