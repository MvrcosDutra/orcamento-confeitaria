package br.com.confeitaria.service;

import br.com.confeitaria.domain.Categoria;
import br.com.confeitaria.domain.Produto;
import br.com.confeitaria.infra.ProdutoRepository;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

public class ProdutoService {

    private final ProdutoRepository repository;

    public ProdutoService(ProdutoRepository repository) {
        this.repository = repository;
    }

    /** US01 — cadastra um produto aplicando a regra RN00. */
    public Produto cadastrar(String nome, Categoria categoria, BigDecimal preco) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome do produto é obrigatório");
        }
        if (categoria == null) {
            throw new IllegalArgumentException("Categoria é obrigatória");
        }
        if (preco == null || preco.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Preço deve ser maior que zero");
        }
        String nomeLimpo = nome.trim();
        if (repository.buscarPorNome(nomeLimpo).isPresent()) {
            throw new IllegalArgumentException("Já existe um produto com o nome \"" + nomeLimpo + "\"");
        }
        return repository.salvar(new Produto(nomeLimpo, categoria, preco));
    }

    /** US02 — lista os produtos em ordem alfabética. */
    public List<Produto> listar() {
        return repository.listarTodos().stream()
                .sorted(Comparator.comparing(Produto::getNome, String.CASE_INSENSITIVE_ORDER))
                .toList();
    }
}
