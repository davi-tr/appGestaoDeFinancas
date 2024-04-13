package com.femass.gestao.domain;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.aspectj.weaver.ast.Not;

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
