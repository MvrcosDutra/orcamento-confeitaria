package br.com.confeitaria.infra;

import br.com.confeitaria.domain.Produto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProdutoRepositoryEmMemoria implements ProdutoRepository {

    private final List<Produto> produtos = new ArrayList<>();
    private long proximoId = 1;

    @Override
    public Produto salvar(Produto produto) {
        produto.setId(proximoId++);
        produtos.add(produto);
        return produto;
    }

    @Override
    public List<Produto> listarTodos() {
        return new ArrayList<>(produtos);
    }

    @Override
    public Optional<Produto> buscarPorNome(String nome) {
        return produtos.stream()
                .filter(p -> p.getNome().equalsIgnoreCase(nome))
                .findFirst();
    }
}
