package br.com.confeitaria.infra;

import br.com.confeitaria.domain.Orcamento;

import java.util.List;

public interface OrcamentoRepository {

    Orcamento salvar(Orcamento orcamento);

    List<Orcamento> listarTodos();
}