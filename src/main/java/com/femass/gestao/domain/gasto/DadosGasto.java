package com.femass.gestao.domain.gasto;

import jakarta.validation.constraints.NotNull;
import org.aspectj.weaver.ast.Not;

import java.math.BigDecimal;

public record DadosGasto(
        Long idGasto,
        @NotNull
        Long idCarteira,
        @NotNull
        BigDecimal valor,
        @NotNull
        boolean eparcela,
        BigDecimal valorParcela,
        @NotNull
        String descricao,
        @NotNull
        String Local,
        int parcelas,
        @NotNull
        Categorias categoria,
        boolean recorrente,
        int periodoRecorrencia
) {
}
