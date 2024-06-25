package com.femass.gestao.domain.gasto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.aspectj.weaver.ast.Not;

import java.math.BigDecimal;

public record DadosGasto(
        Long idGasto,
        @NotNull
        @NotBlank
        @NotEmpty
        Long idCarteira,
        @NotNull
        @NotBlank
        @NotEmpty
        BigDecimal valor,
        @NotNull
        @NotBlank
        @NotEmpty
        boolean eparcela,
        BigDecimal valorParcela,
        @NotNull
        @NotBlank
        @NotEmpty
        String descricao,
        @NotNull
        @NotBlank
        @NotEmpty
        String local,
        int parcelas,
        @NotNull
        @NotBlank
        @NotEmpty
        Categorias categoria,
        boolean recorrente,
        int periodoRecorrencia
) {
}
