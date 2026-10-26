package br.com.confeitaria.service;

import br.com.confeitaria.domain.ItemOrcamento;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

/** US04 — regras de preço do orçamento (RN01, RN02, RN03 em docs/requisitos/requisitos.md). */
public class CalculadoraOrcamento {

    /** Soma de preço × quantidade de todos os itens. */
    public BigDecimal calcularSubtotal(List<ItemOrcamento> itens) {
        throw new UnsupportedOperationException("US04 — em desenvolvimento");
    }

    /** RN01 — 5% a partir de R$ 300,00; 10% a partir de R$ 600,00. */
    public BigDecimal calcularDesconto(BigDecimal subtotal) {
        throw new UnsupportedOperationException("US04 — em desenvolvimento");
    }

    /** RN02 — retirada (null) R$ 0; até 5 km R$ 10; até 15 km R$ 20; acima de 15 km não entrega. */
    public BigDecimal calcularTaxaEntrega(BigDecimal distanciaKm) {
        throw new UnsupportedOperationException("US04 — em desenvolvimento");
    }

    /** RN03 — mínimo de 2 dias de antecedência; 5 dias se houver bolo personalizado. */
    public void validarAntecedencia(LocalDate hoje, LocalDate entrega, boolean temBoloPersonalizado) {
        throw new UnsupportedOperationException("US04 — em desenvolvimento");
    }
}