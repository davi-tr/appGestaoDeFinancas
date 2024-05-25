package com.femass.gestao.domain.gasto;

import jakarta.validation.constraints.NotNull;

public record DadosDelete(
        @NotNull Long id,
        @NotNull Long idCarteira
) {
}
