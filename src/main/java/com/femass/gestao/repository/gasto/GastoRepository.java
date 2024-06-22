package com.femass.gestao.repository.gasto;

import com.femass.gestao.domain.gasto.Gasto;
import com.femass.gestao.domain.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.ZonedDateTime;
import java.util.List;

public interface GastoRepository extends JpaRepository<Gasto, Long> {
    List<Gasto> findByCarteiraId(Long carteiraId);

    List<Gasto> findByCarteiraIdAndDataEntradaBetween(Long carteiraId, ZonedDateTime dataInicio, ZonedDateTime dataFim);
}
