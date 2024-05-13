package com.femass.gestao.domain.carteira;

import com.femass.gestao.domain.gasto.Gasto;

import java.util.List;

public record DadosGastoLimitado(
        Long idCarteira,
        List<Gasto> gastos
) {
}
