package br.com.confeitaria.infra;

import br.com.confeitaria.domain.Orcamento;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrcamentoRepositoryEmMemoria implements OrcamentoRepository {

    private final List<Orcamento> orcamentos = new ArrayList<>();
    private long proximoId = 1;

    @Override
    public Orcamento salvar(Orcamento orcamento) {
        orcamento.setId(proximoId++);
        orcamentos.add(orcamento);
        return orcamento;
    }

    @Override
    public List<Orcamento> listarTodos() {
        return new ArrayList<>(orcamentos);
    }

    @Override
    public Optional<Orcamento> buscarPorId(Long id) {
        return orcamentos.stream()
                .filter(o -> o.getId().equals(id))
                .findFirst();
    }
}
