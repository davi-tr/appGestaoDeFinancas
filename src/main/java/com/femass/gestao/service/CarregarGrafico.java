package com.femass.gestao.service;

import com.femass.gestao.domain.gasto.Categorias;
import com.femass.gestao.domain.gasto.Gasto;
import com.femass.gestao.repository.carteira.CarteiraRepository;
import com.femass.gestao.repository.gasto.GastoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CarregarGrafico {


    @Autowired
    private GastoRepository gastoRepository;

    public Map<String, BigDecimal> calcularGastosPorCategoria(Long carteiraId, ZonedDateTime dataInicio, ZonedDateTime dataFim) {
        // Inicializa o mapa com todas as categorias com valor zero
        Map<Categorias, BigDecimal> gastosPorCategoria = new EnumMap<>(Categorias.class);
        for (Categorias categoria : Categorias.values()) {
            gastosPorCategoria.put(categoria, BigDecimal.ZERO);
        }

        // Obtém a lista de gastos para a carteira especificada dentro do intervalo de datas
        List<Gasto> gastos = gastoRepository.findByCarteiraIdAndDataEntradaBetween(carteiraId, dataInicio, dataFim);

        // Agrupa os gastos por categoria e calcula o total de cada categoria
        Map<Categorias, BigDecimal> gastosAgrupados = gastos.stream()
                .collect(Collectors.groupingBy(
                        Gasto::getCategoria,
                        Collectors.reducing(BigDecimal.ZERO, Gasto::getValor, BigDecimal::add)
                ));

        // Atualiza o mapa inicial com os valores reais dos gastos
        gastosAgrupados.forEach(gastosPorCategoria::put);

        // Converte o mapa para usar nomes das categorias como chaves
        Map<String, BigDecimal> resultado = new java.util.HashMap<>();
        for (Map.Entry<Categorias, BigDecimal> entry : gastosPorCategoria.entrySet()) {
            resultado.put(entry.getKey().name(), entry.getValue());
        }

        return resultado;
    }
}