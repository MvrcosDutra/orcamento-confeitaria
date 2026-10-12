package br.com.confeitaria.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Produto {

    private Long id;
    private final String nome;
    private final Categoria categoria;
    private final BigDecimal preco;

    public Produto(String nome, Categoria categoria, BigDecimal preco) {
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
