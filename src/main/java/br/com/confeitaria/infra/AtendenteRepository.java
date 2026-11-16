
package br.com.confeitaria.infra;

import br.com.confeitaria.domain.Atendente;
import java.util.Optional;

public interface AtendenteRepository {

    Atendente salvar(Atendente atendente);

    Optional<Atendente> buscarPorUsuario(String usuario);
}