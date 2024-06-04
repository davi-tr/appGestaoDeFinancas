package com.femass.gestao.domain.carteira;

import com.femass.gestao.domain.gasto.Gasto;

import java.math.BigDecimal;
import java.util.List;

public record DadosGastoLimitado(
        BigDecimal valorDisponivel,
        Long idCarteira,
        List<Object> listaDeMovimentacoes,
        BigDecimal totalEntradas,
        BigDecimal totalSaidas
) {
}
