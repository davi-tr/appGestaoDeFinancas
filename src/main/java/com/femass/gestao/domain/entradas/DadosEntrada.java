package com.femass.gestao.domain.entradas;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record DadosEntrada(
        Long idEntrada,
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
        String descricao,
        @NotNull
        @NotBlank
        @NotEmpty
        String local,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        String data,
        boolean recorrente,
        int periodoRecorrencia
) {
}
