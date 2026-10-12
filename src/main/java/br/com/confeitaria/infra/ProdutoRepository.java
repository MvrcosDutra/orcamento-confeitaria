package br.com.confeitaria.infra;

import br.com.confeitaria.domain.Produto;

import java.util.List;
import java.util.Optional;

public interface ProdutoRepository {

    Produto salvar(Produto produto);

    List<Produto> listarTodos();

    Optional<Produto> buscarPorNome(String nome);
}
