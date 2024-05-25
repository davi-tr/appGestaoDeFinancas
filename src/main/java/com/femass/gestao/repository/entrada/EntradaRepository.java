package com.femass.gestao.repository.entrada;

import com.femass.gestao.domain.entradas.Entrada;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntradaRepository extends JpaRepository<Entrada, Long> {
}
