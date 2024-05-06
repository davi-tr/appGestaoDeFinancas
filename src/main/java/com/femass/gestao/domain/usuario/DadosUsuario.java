package com.femass.gestao.domain.usuario;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record DadosUsuario(
        @NotNull
        Long id,
        @NotNull
        String Login,

        BigDecimal Saldo
) {
}
