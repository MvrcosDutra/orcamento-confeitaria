package br.com.confeitaria.infra;

import br.com.confeitaria.domain.Orcamento;

import java.util.ArrayList;
import java.util.List;

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
}