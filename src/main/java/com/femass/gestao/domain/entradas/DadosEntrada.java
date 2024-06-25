package com.femass.gestao.domain.entradas;

import com.fasterxml.jackson.annotation.JsonFormat;
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
        String Local,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        String data,
        boolean recorrente,
        int periodoRecorrencia
) {
}
