
package br.com.confeitaria.service;

import br.com.confeitaria.domain.Atendente;
import br.com.confeitaria.infra.AtendenteRepository;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

/**
 * US10 — cadastro e login de atendentes.
 */
public class AutenticacaoService {

    private static final int TAMANHO_MINIMO_SENHA = 6;

    private final AtendenteRepository repository;

    public AutenticacaoService(AtendenteRepository repository) {
        this.repository = repository;
    }

    /**
     * Cadastra um atendente guardando apenas o hash da senha (RNF07).
     */
    public Atendente cadastrarAtendente(String usuario, String senha) {

        if (usuario == null || usuario.isBlank()) {
            throw new IllegalArgumentException("Usuário é obrigatório");
        }

        if (senha == null || senha.length() < TAMANHO_MINIMO_SENHA) {
            throw new IllegalArgumentException(
                    "Senha deve ter pelo menos " + TAMANHO_MINIMO_SENHA + " caracteres"
            );
        }

        String usuarioLimpo = usuario.trim();

        if (repository.buscarPorUsuario(usuarioLimpo).isPresent()) {
            throw new IllegalArgumentException(
                    "Usuário \"" + usuarioLimpo + "\" já existe"
            );
        }

        return repository.salvar(
                new Atendente(usuarioLimpo, gerarHash(senha))
        );
    }

    /**
     * Devolve true se o usuário existe e a senha confere.
     */
    public boolean autenticar(String usuario, String senha) {

        if (usuario == null || senha == null) {
            return false;
        }

        return repository.buscarPorUsuario(usuario.trim())
                .map(atendente ->
                        atendente.getSenhaHash().equals(gerarHash(senha))
                )
                .orElse(false);
    }

    /**
     * Hash SHA-256 da senha, em hexadecimal (64 caracteres).
     */
    public static String gerarHash(String senha) {

        try {
            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");

            return HexFormat.of().formatHex(
                    sha256.digest(senha.getBytes(StandardCharsets.UTF_8))
            );

        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 indisponível", e);
        }
    }
}
