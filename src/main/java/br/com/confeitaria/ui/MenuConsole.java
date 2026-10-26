package br.com.confeitaria.ui;

import br.com.confeitaria.domain.Categoria;
import br.com.confeitaria.domain.Produto;
import br.com.confeitaria.service.OrcamentoService;
import br.com.confeitaria.service.ProdutoService;

import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class MenuConsole {

    private final ProdutoService produtoService;
    private final Scanner entrada;
    private final PrintStream saida;
    private final TelaNovoOrcamento telaNovoOrcamento;
    private final TelaListaOrcamentos telaListaOrcamentos;

    public MenuConsole(ProdutoService produtoService,
                       OrcamentoService orcamentoService,
                       Scanner entrada,
                       PrintStream saida) {
        this.produtoService = produtoService;
        this.entrada = entrada;
        this.saida = saida;
        this.telaNovoOrcamento = new TelaNovoOrcamento(
                produtoService, orcamentoService, entrada, saida);
        this.telaListaOrcamentos = new TelaListaOrcamentos(
                orcamentoService, entrada, saida);
    }

    public void iniciar() {
        saida.println("=== Orçamento Confeitaria ===");

        while (true) {
            saida.println();
            saida.println("1 - Cadastrar produto");
            saida.println("2 - Listar produtos");
            saida.println("3 - Novo orçamento");
            saida.println("4 - Listar orçamentos");
            saida.println("0 - Sair");
            saida.print("Escolha uma opção: ");

            if (!entrada.hasNextLine()) {
                return;
            }

            switch (entrada.nextLine().trim()) {
                case "1" -> cadastrarProduto();
                case "2" -> listarProdutos();
                case "3" -> telaNovoOrcamento.executar();
                case "4" -> telaListaOrcamentos.executar();
                case "0" -> {
                    saida.println("Até logo!");
                    return;
                }
                default -> saida.println(
                        "Opção inválida. Digite um número de 0 a 4.");
            }
        }
    }

    private void cadastrarProduto() {
        saida.print("Nome: ");
        String nome = entrada.nextLine();

        Categoria[] categorias = Categoria.values();

        for (int i = 0; i < categorias.length; i++) {
            saida.println((i + 1) + " - " + categorias[i].getDescricao());
        }

        saida.print("Categoria (número): ");

        Categoria categoria;

        try {
            categoria = categorias[
                    Integer.parseInt(entrada.nextLine().trim()) - 1
            ];
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            saida.println(
                    "Categoria inválida. Digite um número de 1 a "
                            + categorias.length + ".");
            return;
        }

        saida.print("Preço (ex.: 45,90): ");

        BigDecimal preco;

        try {
            preco = new BigDecimal(
                    entrada.nextLine().trim().replace(",", "."));
        } catch (NumberFormatException e) {
            saida.println(
                    "Preço inválido. Use apenas números, por exemplo 45,90.");
            return;
        }

        try {
            Produto produto = produtoService.cadastrar(
                    nome, categoria, preco);

            saida.println(
                    "Produto cadastrado com código "
                            + produto.getId() + ".");
        } catch (IllegalArgumentException e) {
            saida.println("Erro: " + e.getMessage());
        }
    }

    private void listarProdutos() {
        List<Produto> produtos = produtoService.listar();

        if (produtos.isEmpty()) {
            saida.println("Nenhum produto cadastrado.");
            return;
        }

        saida.printf(
                "%-6s %-30s %-10s %10s%n",
                "Código", "Nome", "Categoria", "Preço");

        for (Produto p : produtos) {
            saida.printf(
                    "%-6d %-30s %-10s %10s%n",
                    p.getId(),
                    p.getNome(),
                    p.getCategoria().getDescricao(),
                    "R$ " + p.getPreco().toString().replace(".", ","));
        }
    }
}