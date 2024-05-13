package com.femass.gestao.domain.usuario;

import java.math.BigDecimal;

public record DadosUnicoUsuario(
        String nome, String email, String numeroTelefone, String login, Long IdCarteira, BigDecimal saldoCarteira
) {
}
