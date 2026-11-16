package br.com.confeitaria.ui;
import br.com.confeitaria.infra.AtendenteRepositoryEmMemoria;
import br.com.confeitaria.infra.OrcamentoRepositoryEmMemoria; 
import br.com.confeitaria.infra.ProdutoRepository; 
import br.com.confeitaria.infra.ProdutoRepositoryEmMemoria; 
import br.com.confeitaria.service.AutenticacaoService; 
import br.com.confeitaria.service.CalculadoraOrcamento; 
import br.com.confeitaria.service.OrcamentoService; 
import br.com.confeitaria.service.ProdutoService; 
import br.com.confeitaria.ui.MenuConsole; 
import br.com.confeitaria.ui.TelaLogin;

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

        AutenticacaoService autenticacaoService =
                new AutenticacaoService(
                        new AtendenteRepositoryEmMemoria()
                );

        autenticacaoService.cadastrarAtendente(
                "admin",
                "confeitaria123"
        );

        Scanner entrada = new Scanner(System.in);

        TelaLogin login =
                new TelaLogin(autenticacaoService, entrada, System.out);

        if (!login.executar()) {
            return;
        }

        MenuConsole menu =
                new MenuConsole(
                        produtoService,
                        orcamentoService,
                        entrada,
                        System.out
                );

        menu.iniciar();
    }
}