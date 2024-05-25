package com.femass.gestao.domain.entradas;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record DadosEntrada(
        Long idEntrada,
        @NotNull
        Long idCarteira,
        @NotNull
        BigDecimal valor,
        @NotNull
        String descricao,
        @NotNull
        String Local
) {
}
