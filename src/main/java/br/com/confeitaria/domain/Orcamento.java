package br.com.confeitaria.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

public class Orcamento {

    private Long id;
    private final Cliente cliente;
    private final List<ItemOrcamento> itens;
    private final LocalDate dataCriacao;
    private final LocalDate dataEntrega;
    private final BigDecimal distanciaKm;
    private StatusOrcamento status = StatusOrcamento.PENDENTE;
    private BigDecimal subtotal = BigDecimal.ZERO;
    private BigDecimal desconto = BigDecimal.ZERO;
    private BigDecimal taxaEntrega = BigDecimal.ZERO;
    private BigDecimal total = BigDecimal.ZERO;

    /** null significa retirada no balcão. */
    public Orcamento(Cliente cliente, List<ItemOrcamento> itens,
                     LocalDate dataCriacao, LocalDate dataEntrega,
                     BigDecimal distanciaKm) {

        this.cliente = cliente;
        this.itens = itens == null ? List.of() : List.copyOf(itens);
        this.dataCriacao = dataCriacao;
        this.dataEntrega = dataEntrega;
        this.distanciaKm = distanciaKm;
    }

    public boolean isRetirada() {
        return distanciaKm == null;
    }

    /** Verifica se existe algum bolo personalizado. */
    public boolean temBoloPersonalizado() {
        return itens.stream()
                .anyMatch(i -> i.isPersonalizado()
                        && i.getProduto().getCategoria() == Categoria.BOLO);
    }

    /** Calcula o total: subtotal - desconto + taxa de entrega. */
    public void aplicarValores(BigDecimal subtotal,
                              BigDecimal desconto,
                              BigDecimal taxaEntrega) {

        this.subtotal = subtotal.setScale(2, RoundingMode.HALF_UP);
        this.desconto = desconto.setScale(2, RoundingMode.HALF_UP);
        this.taxaEntrega = taxaEntrega.setScale(2, RoundingMode.HALF_UP);

        this.total = this.subtotal
                .subtract(this.desconto)
                .add(this.taxaEntrega);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public List<ItemOrcamento> getItens() {
        return itens;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public LocalDate getDataEntrega() {
        return dataEntrega;
    }

    public BigDecimal getDistanciaKm() {
        return distanciaKm;
    }

    public StatusOrcamento getStatus() {
        return status;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public BigDecimal getDesconto() {
        return desconto;
    }

    public BigDecimal getTaxaEntrega() {
        return taxaEntrega;
    }

    public BigDecimal getTotal() {
        return total;
    }
}