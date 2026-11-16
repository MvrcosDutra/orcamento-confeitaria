
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

    public Produto cadastrar(String nome, Categoria categoria, BigDecimal preco) {
        String nomeLimpo = validar(nome, categoria, preco);

        if (repository.buscarPorNome(nomeLimpo).isPresent()) {
            throw new IllegalArgumentException(
                    "Já existe um produto com o nome \"" + nomeLimpo + "\"");
        }

        return repository.salvar(new Produto(nomeLimpo, categoria, preco));
    }

    public Produto editar(Long id, String nome, Categoria categoria, BigDecimal preco) {
        Produto produto = buscarPorId(id);
        String nomeLimpo = validar(nome, categoria, preco);

        repository.buscarPorNome(nomeLimpo)
                .filter(outro -> !outro.getId().equals(id))
                .ifPresent(outro -> {
                    throw new IllegalArgumentException(
                            "Já existe um produto com o nome \"" + nomeLimpo + "\"");
                });

        produto.atualizar(nomeLimpo, categoria, preco);
        return produto;
    }

    public void excluir(Long id) {
        buscarPorId(id);
        repository.remover(id);
    }

    public List<Produto> buscar(String termo) {
        if (termo == null || termo.isBlank()) {
            throw new IllegalArgumentException(
                    "Digite parte do nome ou a categoria para buscar");
        }

        String procurado = termo.trim().toLowerCase();

        return listar().stream()
                .filter(p -> p.getNome().toLowerCase().contains(procurado)
                        || p.getCategoria().getDescricao().toLowerCase().contains(procurado))
                .toList();
    }

    public Produto buscarPorId(Long id) {
        return repository.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado"));
    }

    public List<Produto> listar() {
        return repository.listarTodos().stream()
                .sorted(Comparator.comparing(Produto::getNome, String.CASE_INSENSITIVE_ORDER))
                .toList();
    }

    private String validar(String nome, Categoria categoria, BigDecimal preco) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome do produto é obrigatório");
        }

        if (categoria == null) {
            throw new IllegalArgumentException("Categoria é obrigatória");
        }

        if (preco == null || preco.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Preço deve ser maior que zero");
        }

        return nome.trim();
    }
}
