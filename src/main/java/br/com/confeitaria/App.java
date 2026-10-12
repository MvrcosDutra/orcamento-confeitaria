package br.com.confeitaria;

import br.com.confeitaria.infra.ProdutoRepository;
import br.com.confeitaria.infra.ProdutoRepositoryEmMemoria;
import br.com.confeitaria.service.ProdutoService;
import br.com.confeitaria.ui.MenuConsole;

import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        ProdutoRepository produtoRepository = new ProdutoRepositoryEmMemoria();
        ProdutoService produtoService = new ProdutoService(produtoRepository);
        MenuConsole menu = new MenuConsole(produtoService, new Scanner(System.in), System.out);
        menu.iniciar();
    }
}
