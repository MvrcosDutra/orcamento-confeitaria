package br.com.confeitaria;

import br.com.confeitaria.infra.OrcamentoRepositoryEmMemoria;
import br.com.confeitaria.infra.ProdutoRepository;
import br.com.confeitaria.infra.ProdutoRepositoryEmMemoria;
import br.com.confeitaria.service.CalculadoraOrcamento;
import br.com.confeitaria.service.OrcamentoService;
import br.com.confeitaria.service.ProdutoService;
import br.com.confeitaria.ui.MenuConsole;

import java.time.Clock;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {

        ProdutoRepository produtoRepository =
                new ProdutoRepositoryEmMemoria();

        ProdutoService produtoService =
                new ProdutoService(produtoRepository);

        OrcamentoService orcamentoService =
                new OrcamentoService(
                        new OrcamentoRepositoryEmMemoria(),
                        new CalculadoraOrcamento(),
                        Clock.systemDefaultZone()
                );

        MenuConsole menu =
                new MenuConsole(
                        produtoService,
                        orcamentoService,
                        new Scanner(System.in),
                        System.out
                );

        menu.iniciar();
    }
}