package br.com.confeitaria.infra;

import br.com.confeitaria.domain.Orcamento;

import java.util.List;
import java.util.Optional;

public interface OrcamentoRepository {

    Orcamento salvar(Orcamento orcamento);

    List<Orcamento> listarTodos();

    Optional<Orcamento> buscarPorId(Long id);
}
