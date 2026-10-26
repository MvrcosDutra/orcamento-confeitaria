
package br.com.confeitaria.service;

import br.com.confeitaria.domain.ItemOrcamento;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * US04 — regras de preço do orçamento
 * (RN01, RN02, RN03 em docs/requisitos/requisitos.md).
 */
public class CalculadoraOrcamento {

    /** Soma de preço × quantidade de todos os itens. */
    public BigDecimal calcularSubtotal(List<ItemOrcamento> itens) {
        BigDecimal subtotal = BigDecimal.ZERO;

        for (ItemOrcamento item : itens) {
            subtotal = subtotal.add(item.getSubtotal());
        }

        return subtotal.setScale(2, RoundingMode.HALF_UP);
    }

    /** RN01 — 5% a partir de R$ 300,00; 10% a partir de R$ 600,00. */
    public BigDecimal calcularDesconto(BigDecimal subtotal) {
        BigDecimal percentual;

        if (subtotal.compareTo(new BigDecimal("600")) >= 0) {
            percentual = new BigDecimal("0.10");
        } else if (subtotal.compareTo(new BigDecimal("300")) >= 0) {
            percentual = new BigDecimal("0.05");
        } else {
            percentual = BigDecimal.ZERO;
        }

        return subtotal.multiply(percentual)
                .setScale(2, RoundingMode.HALF_UP);
    }

    /** RN02 — retirada (null) R$ 0; até 5 km R$ 10;
     * até 15 km R$ 20; acima de 15 km não entrega.
     */
    public BigDecimal calcularTaxaEntrega(BigDecimal distanciaKm) {
        if (distanciaKm == null) {
            return new BigDecimal("0.00");
        }

        if (distanciaKm.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(
                    "Distância não pode ser negativa");
        }

        if (distanciaKm.compareTo(new BigDecimal("5")) <= 0) {
            return new BigDecimal("10.00");
        }

        if (distanciaKm.compareTo(new BigDecimal("15")) <= 0) {
            return new BigDecimal("20.00");
        }

        throw new IllegalArgumentException(
                "Entrega disponível somente até 15 km. "
                + "Escolha retirada no balcão");
    }

    /** RN03 — mínimo de 2 dias; 5 dias para bolo personalizado. */
    public void validarAntecedencia(
            LocalDate hoje,
            LocalDate entrega,
            boolean temBoloPersonalizado) {

        long minimo = temBoloPersonalizado ? 5 : 2;
        long dias = ChronoUnit.DAYS.between(hoje, entrega);

        if (dias < minimo) {
            throw new IllegalArgumentException(
                    "Entrega exige no mínimo " + minimo
                    + " dias de antecedência");
        }
    }
}
