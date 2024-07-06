package com.femass.gestao.repository.entrada;

import com.femass.gestao.domain.entradas.Entrada;
import com.femass.gestao.domain.gasto.Gasto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EntradaRepository extends JpaRepository<Entrada, Long> {
    @Query("Select e from Entrada e where e.idEntradaInicial = :id")
    List<Entrada> findByIdEntradaInicial(@Param("id") Long id);
}
