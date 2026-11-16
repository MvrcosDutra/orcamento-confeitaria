
package br.com.confeitaria.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Produto {

    private Long id;
    private String nome;
    private Categoria categoria;
    private BigDecimal preco;

    public Produto(String nome, Categoria categoria, BigDecimal preco) {
        this.nome = nome;
        this.categoria = categoria;
        this.preco = preco.setScale(2, RoundingMode.HALF_UP);
    }

    /** US06 — altera os dados do produto. As validações ficam no ProdutoService. */
    public void atualizar(String nome, Categoria categoria, BigDecimal preco) {
        this.nome = nome;
        this.categoria = categoria;
        this.preco = preco.setScale(2, RoundingMode.HALF_UP);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public BigDecimal getPreco() {
        return preco;
    }
}
