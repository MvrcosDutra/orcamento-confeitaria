package br.com.confeitaria.domain;

import java.math.BigDecimal;

public class ItemOrcamento {

    private final Produto produto;
    private final int quantidade;
    private final String observacao;
    private final boolean personalizado;

    public ItemOrcamento(Produto produto, int quantidade, String observacao, boolean personalizado) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.observacao = observacao;
        this.personalizado = personalizado;
    }

    /** Preço do produto × quantidade. */
    public BigDecimal getSubtotal() {
        return produto.getPreco().multiply(BigDecimal.valueOf(quantidade));
    }

    public Produto getProduto() {
        return produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public String getObservacao() {
        return observacao;
    }

    public boolean isPersonalizado() {
        return personalizado;
    }
}