package com.femass.gestao.domain.usuario;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record DadosCadastroUsuario(
        @NotNull
        String email,
        @NotNull
        @Valid
        String numeroTelefone,
        @NotNull
        String nome,
        @NotNull
        @Valid
        String password,
        @NotNull
        @Valid
        String login
) {
}
