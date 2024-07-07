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

    @Query("SELECT g FROM Gasto g WHERE g.carteira.id = :carteiraId AND g.dataEntrada BETWEEN :dataInicio AND :dataFim")
    List<Gasto> findByCarteiraIdAndDataEntradaBetween(
            @Param("carteiraId") Long carteiraId,
            @Param("dataInicio") ZonedDateTime dataInicio,
            @Param("dataFim") ZonedDateTime dataFim);

    @Query("Select g from Gasto g where g.idGastoInicial = :id")
    List<Gasto> findByIdGastoInicial(@Param("id") Long id);
}
