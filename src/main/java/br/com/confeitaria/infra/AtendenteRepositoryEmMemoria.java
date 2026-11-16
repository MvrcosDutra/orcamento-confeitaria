
package br.com.confeitaria.infra;

import br.com.confeitaria.domain.Atendente;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AtendenteRepositoryEmMemoria
        implements AtendenteRepository {

    private final List<Atendente> atendentes = new ArrayList<>();

    @Override
    public Atendente salvar(Atendente atendente) {
        atendentes.add(atendente);
        return atendente;
    }

    @Override
    public Optional<Atendente> buscarPorUsuario(String usuario) {
        return atendentes.stream()
                .filter(a -> a.getUsuario().equalsIgnoreCase(usuario))
                .findFirst();
    }
}