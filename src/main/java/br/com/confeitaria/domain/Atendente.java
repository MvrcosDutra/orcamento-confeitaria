
package br.com.confeitaria.domain;

/**
 * US10 — atendente que acessa o sistema.
 * A senha é guardada só como hash (RNF07).
 */
public class Atendente {

    private final String usuario;
    private final String senhaHash;

    public Atendente(String usuario, String senhaHash) {
        this.usuario = usuario;
        this.senhaHash = senhaHash;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getSenhaHash() {
        return senhaHash;
    }
}