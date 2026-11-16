
package br.com.confeitaria.ui;

import br.com.confeitaria.service.AutenticacaoService;

import java.io.Console;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * US10 — tela de login.
 * Após 3 tentativas erradas o sistema encerra.
 */
public class TelaLogin {

    private static final int MAXIMO_TENTATIVAS = 3;

    private final AutenticacaoService autenticacaoService;
    private final Scanner entrada;
    private final PrintStream saida;

    public TelaLogin(
            AutenticacaoService autenticacaoService,
            Scanner entrada,
            PrintStream saida) {
        this.autenticacaoService = autenticacaoService;
        this.entrada = entrada;
        this.saida = saida;
    }

    public boolean executar() {
        saida.println("=== Login do atendente ===");

        for (int tentativa = 1; tentativa <= MAXIMO_TENTATIVAS; tentativa++) {
            saida.print("Usuário: ");

            if (!entrada.hasNextLine()) {
                return false;
            }

            String usuario = entrada.nextLine();
            String senha = lerSenha();

            if (autenticacaoService.autenticar(usuario, senha)) {
                saida.println("Bem-vindo(a), " + usuario.trim() + "!");
                return true;
            }

            int restantes = MAXIMO_TENTATIVAS - tentativa;

            if (restantes > 0) {
                saida.println(
                        "Usuário ou senha incorretos. Tentativas restantes: "
                                + restantes
                );
            }
        }

        saida.println("Acesso bloqueado: 3 tentativas incorretas.");
        return false;
    }

    private String lerSenha() {
        Console console = System.console();

        if (console != null) {
            char[] senha = console.readPassword("Senha: ");
            return senha == null ? "" : new String(senha);
        }

        saida.print("Senha: ");
        return entrada.hasNextLine() ? entrada.nextLine() : "";
    }
}