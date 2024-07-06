package com.femass.gestao.repository.gasto;

import com.femass.gestao.domain.gasto.Gasto;
import com.femass.gestao.domain.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.ZonedDateTime;
import java.util.List;

public interface GastoRepository extends JpaRepository<Gasto, Long> {
    List<Gasto> findByCarteiraId(Long carteiraId);

    List<Gasto> findByCarteiraIdAndDataEntradaBetween(Long carteiraId, ZonedDateTime dataInicio, ZonedDateTime dataFim);

    @Query("Select g from Gasto g where g.idGastoInicial = :id")
    List<Gasto> findByIdGastoInicial(@Param("id") Long id);
}
