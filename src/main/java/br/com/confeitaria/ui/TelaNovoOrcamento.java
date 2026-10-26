
package br.com.confeitaria.ui;

import br.com.confeitaria.domain.Cliente;
import br.com.confeitaria.domain.ItemOrcamento;
import br.com.confeitaria.domain.Orcamento;
import br.com.confeitaria.domain.Produto;
import br.com.confeitaria.service.OrcamentoService;
import br.com.confeitaria.service.ProdutoService;

import java.io.PrintStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
        try {
            List<Produto> produtos = produtoService.listar();

            if (produtos.isEmpty()) {
                saida.println("Nenhum produto cadastrado. Cadastre um produto primeiro.");
                return;
            }

            saida.println("\n=== NOVO ORÇAMENTO ===");
            saida.println("\nProdutos disponíveis:");

            for (Produto produto : produtos) {
                saida.printf("%d - %s - R$ %s%n",
                        produto.getId(), produto.getNome(), produto.getPreco());
            }

            saida.print("\nNome do cliente: ");
            String nome = entrada.nextLine().trim();

            saida.print("Telefone do cliente: ");
            String telefone = entrada.nextLine().trim();

            Cliente cliente = new Cliente(nome, telefone);
            List<ItemOrcamento> itens = new ArrayList<>();

            while (true) {
                saida.print("\nID do produto (Enter para finalizar): ");
                String codigo = entrada.nextLine().trim();

                if (codigo.isEmpty()) {
                    if (itens.isEmpty()) {
                        saida.println("Adicione pelo menos um item.");
                        continue;
                    }
                    break;
                }

                try {
                    Produto produto = produtoService.buscarPorId(
                            Long.parseLong(codigo));

                    saida.print("Quantidade: ");
                    int quantidade = Integer.parseInt(entrada.nextLine().trim());

                    saida.print("Observação (Enter para nenhuma): ");
                    String observacao = entrada.nextLine().trim();

                    boolean personalizado = false;
                    if (produto.getCategoria().name().equalsIgnoreCase("BOLO")) {
                        saida.print("Bolo personalizado? (S/N): ");
                        personalizado = entrada.nextLine().trim()
                                .equalsIgnoreCase("S");
                    }

                    itens.add(new ItemOrcamento(
                            produto, quantidade, observacao, personalizado));
                    saida.println("Item adicionado!");

                } catch (NumberFormatException e) {
                    saida.println("Digite um ID e uma quantidade válidos.");
                } catch (IllegalArgumentException e) {
                    saida.println("Erro: " + e.getMessage());
                }
            }

            LocalDate dataEntrega;
            while (true) {
                saida.print("\nData de entrega (dd/MM/yyyy): ");
                try {
                    dataEntrega = LocalDate.parse(
                            entrada.nextLine().trim(),
                            DateTimeFormatter.ofPattern("dd/MM/uuuu"));
                    break;
                } catch (DateTimeParseException e) {
                    saida.println("Data inválida. Tente novamente.");
                }
            }

            saida.print("Recebimento: (E)ntrega ou (R)etirada: ");
            String forma = entrada.nextLine().trim();
            BigDecimal distanciaKm = null;

            if (forma.equalsIgnoreCase("E")) {
                saida.print("Distância em km: ");
                try {
                    distanciaKm = new BigDecimal(
                            entrada.nextLine().trim().replace(",", "."));
                } catch (NumberFormatException e) {
                    saida.println("Distância inválida.");
                    return;
                }
            } else if (!forma.equalsIgnoreCase("R")) {
                saida.println("Opção inválida.");
                return;
            }

            Orcamento orcamento = orcamentoService.criar(
                    cliente, itens, dataEntrega, distanciaKm);

            saida.println("\n=== ORÇAMENTO CRIADO ===");
            saida.println("Código: " + orcamento.getId());
            saida.println("Status: " + orcamento.getStatus());
            saida.println("Cliente: " + orcamento.getCliente().getNome());
            saida.println("Data de entrega: " + orcamento.getDataEntrega());
            saida.println("Recebimento: "
                    + (orcamento.isRetirada() ? "Retirada" : "Entrega"));
            saida.println("Subtotal: R$ " + orcamento.getSubtotal());
            saida.println("Desconto: R$ " + orcamento.getDesconto());
            saida.println("Taxa de entrega: R$ " + orcamento.getTaxaEntrega());
            saida.println("Total: R$ " + orcamento.getTotal());

        } catch (IllegalArgumentException e) {
            saida.println("\nNão foi possível criar o orçamento: " + e.getMessage());
        }
    }
}
